package com.basetwitter.basetwitter.service;

import com.basetwitter.basetwitter.ApplicationConfig;
import com.basetwitter.basetwitter.client.BaseDbClient;
import com.basetwitter.basetwitter.models.Contracts;
import com.basetwitter.basetwitter.models.contractdetails.ContractDetails;
import com.basetwitter.basetwitter.repository.ContractDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FetchBaseServiceImpl implements FetchBaseService {
  private static final int NUMBER_OF_STEPS = -1000;

  private static final Logger logger = LoggerFactory.getLogger(FetchBaseService.class);
  final BaseDbClient baseDbClient;
  ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

  @Value("${base.url.results}")
  String baseUrlResults;

  @Value("${base.url.contracts}")
  String baseUrlContracts;

  final
  ContractDetailsRepository contractDetailsRepository;

  public FetchBaseServiceImpl(BaseDbClient baseDbClient,
      ContractDetailsRepository contractDetailsRepository) {
    this.baseDbClient = baseDbClient;
    this.contractDetailsRepository = contractDetailsRepository;
  }

  @Override
  public int insertContracts() throws IOException {

    List<Contracts> contractsList = new ArrayList<>();
    Integer numberOfContracts = getNumberOfContracts();
    URL url = new URL(baseUrlContracts);

    int lowerRange;
    int upperRange;
    int size = 0;
    for (int i = numberOfContracts; i > 0; i = i + NUMBER_OF_STEPS) {
      lowerRange = 1;
      if (i > Math.abs(NUMBER_OF_STEPS)) {
        lowerRange = i + NUMBER_OF_STEPS + 1;
      }
      upperRange = i;

      BufferedReader resp = getBaseResponseBufferedReader(url, lowerRange, upperRange);
      StringBuilder build = new StringBuilder();
      String inputLine;

      while ((inputLine = resp.readLine()) != null) {
        build.append(inputLine);
      }
      contractsList = mapContractsList(build);
      size = insertContractsList(contractsList);
    }
    return size;
  }

  @Override
  public void insertContractDetails()  {
    List<Contracts> contractsList = baseDbClient.getListofIdsNotInContractDetails();

    contractsList
        .stream()
        .map(this::getBaseContractDetails).forEach(contractDetailsRepository::save);
  }

  private ContractDetails getBaseContractDetails(Contracts contracts) {
    ContractDetails contractDetails = new ContractDetails();

    try{
    URL url = new URL(baseUrlContracts + "/" + contracts.getId());
    BufferedReader resp = getBaseResponseBufferedReader(url);
    StringBuilder build = new StringBuilder();
    String inputLine;

    while ((inputLine = resp.readLine()) != null) {
      build.append(inputLine);
      contractDetails = mapContractDetails(build);
    }
    } catch (IOException e){
      e.printStackTrace();
    }
    return contractDetails;
  }
  private List<Contracts> mapContractsList(StringBuilder build) {
    ObjectMapper mapper = (ObjectMapper) context.getBean("objectMapper");
    List<Contracts> contractsList = new ArrayList<>();
    try {
      Contracts[] contracts = mapper.readValue(build.toString(), Contracts[].class);
      contractsList = Arrays.asList(contracts);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return contractsList;
  }

  private ContractDetails mapContractDetails(StringBuilder build) {
    ObjectMapper mapper = (ObjectMapper) context.getBean("objectMapper");
    ContractDetails contractDetails = new ContractDetails();
    try {
      contractDetails = mapper.readValue(build.toString(), ContractDetails.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return contractDetails;
  }

  private BufferedReader getBaseResponseBufferedReader(URL url, int lowerRange, int upperRange)
      throws IOException {
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestProperty("range", String.format("%s-%s", lowerRange, upperRange));

    return new BufferedReader(new InputStreamReader(conn.getInputStream()));
  }

  private BufferedReader getBaseResponseBufferedReader(URL url) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    return new BufferedReader(new InputStreamReader(conn.getInputStream()));
  }

  private Integer getNumberOfContracts() throws IOException {
    URL url = new URL(baseUrlResults);
    BufferedReader rd = getBaseResponseBufferedReader(url);

    Integer result;
    result = getNContractsFromSpan(rd);
    return result;
  }

  private Integer getNContractsFromSpan(BufferedReader rd) throws IOException {
    String res = "";
    String line;
    while ((line = rd.readLine()) != null) {
      if (line.contains("Foram encontrados")) {
        Document doc = Jsoup.parse(line);
        for (Element e : doc.select("span")) {
          res = e.text();
        }
      }
    }
    return Integer.parseInt(res);
  }

  private int insertContractsList(List<Contracts> contractsList) {
    return baseDbClient.insertContracts(contractsList);
  }
}
