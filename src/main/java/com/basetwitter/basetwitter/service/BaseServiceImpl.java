package com.basetwitter.basetwitter.service;

import com.basetwitter.basetwitter.ApplicationConfig;
import com.basetwitter.basetwitter.client.BaseDbClient;
import com.basetwitter.basetwitter.client.BaseHttpClient;
import com.basetwitter.basetwitter.models.Contracts;
import com.basetwitter.basetwitter.models.contractdetails.ContractDetails;
import com.basetwitter.basetwitter.repository.ContractDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
  private static final int NUMBER_OF_STEPS = -1000;

  private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
  final BaseDbClient baseDbClient;
  final ContractDetailsRepository contractDetailsRepository;
  final BaseHttpClient baseHttpClient;
  ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

  @Value("${base.url.results}")
  String baseUrlResults;

  @Value("${base.url.contracts}")
  String baseUrlContracts;

  public BaseServiceImpl(
      BaseDbClient baseDbClient,
      ContractDetailsRepository contractDetailsRepository,
      BaseHttpClient baseHttpClient) {
    this.baseDbClient = baseDbClient;
    this.contractDetailsRepository = contractDetailsRepository;
    this.baseHttpClient = baseHttpClient;
  }

  @Override
  public void insertContracts() throws IOException {
    URL urlResults = new URL(baseUrlResults);
    List<Contracts> contractsList;
    Integer numberOfContracts = baseHttpClient.getNumberOfContracts(urlResults);
    URL urlContracts = new URL(baseUrlContracts);

    for (int upperRange = numberOfContracts;
        upperRange > 0;
        upperRange = upperRange + NUMBER_OF_STEPS) {
      int lowerRange = 1;
      if (upperRange > Math.abs(NUMBER_OF_STEPS)) {
        lowerRange = upperRange + NUMBER_OF_STEPS + 1;
      }

      BufferedReader resp =
          baseHttpClient.getBaseResponseBufferedReader(urlContracts, lowerRange, upperRange);
      StringBuilder build = new StringBuilder();
      String inputLine;

      while ((inputLine = resp.readLine()) != null) {
        build.append(inputLine);
      }
      contractsList = mapContractsList(build);
      insertContractsList(contractsList);
    }
  }

  @Override
  public void insertContractDetails() {
    List<Contracts> contractsList = baseDbClient.getListofIdsNotInContractDetails();

    contractsList.stream()
        .map(this::getBaseContractDetails)
        .forEach(contractDetailsRepository::save);
  }

  private ContractDetails getBaseContractDetails(Contracts contracts) {
    ContractDetails contractDetails = new ContractDetails();

    try {
      URL url = new URL(baseUrlContracts + "/" + contracts.getId());
      BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url);
      StringBuilder build = new StringBuilder();
      String inputLine;

      while ((inputLine = resp.readLine()) != null) {
        build.append(inputLine);
        contractDetails = mapContractDetails(build);
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
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

  private void insertContractsList(List<Contracts> contractsList) {
    baseDbClient.insertContracts(contractsList);
  }
}
