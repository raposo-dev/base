package com.base.base.service;

import com.base.base.ApplicationConfig;
import com.base.base.client.BaseDbClient;
import com.base.base.client.BaseHttpClient;
import com.base.base.models.Contracts;
import com.base.base.models.contractdetails.ContractDetails;
import com.base.base.repository.ContractDetailsRepository;
import com.base.base.repository.ContractsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
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
  final ContractsRepository contractsRepository;
  ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

  @Value("${base.url.results}")
  String baseUrlResults;

  @Value("${base.url.contracts}")
  String baseUrlContracts;

  public BaseServiceImpl(
      BaseDbClient baseDbClient,
      ContractDetailsRepository contractDetailsRepository,
      BaseHttpClient baseHttpClient,
      ContractsRepository contractsRepository) {
    this.baseDbClient = baseDbClient;
    this.contractDetailsRepository = contractDetailsRepository;
    this.baseHttpClient = baseHttpClient;
    this.contractsRepository = contractsRepository;
  }

  @Override
  public void insertContracts() throws IOException {
    List<Contracts> contractsList;
    Integer numberOfContracts = 1387283;
    logger.info("Got {} contracts from Base", numberOfContracts);
    logger.info("Inserting missing Contracts into Contracts Table.");

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
    logger.info("Getting Contract Details for {} contracts.", contractsList.size());
    contractsList.stream()
        .map(this::getBaseContractDetails)
        .forEach(contractDetailsRepository::save);
  }

  private ContractDetails getBaseContractDetails(Contracts contracts) {

    try {
      URL url = new URL(baseUrlContracts + "/" + contracts.getId());
      BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url);
      StringBuilder build = new StringBuilder();
      String inputLine;

      while ((inputLine = resp.readLine()) != null) {
        build.append(inputLine);
      }
      ContractDetails contractDetails = mapContractDetails(build);
      if (contractDetails.getId() == 0){
        contractDetails.setId(contracts.getId());
      }

      return contractDetails;
    } catch (IOException e) {
      logger.error("Error occurred during request process", e);
      throw new RuntimeException(e);
    }

  }

  private List<Contracts> mapContractsList(StringBuilder build) {
    ObjectMapper mapper = (ObjectMapper) context.getBean("objectMapper");
    List<Contracts> contractsList;
    try {
      Contracts[] contracts = mapper.readValue(build.toString(), Contracts[].class);
      contractsList = Arrays.asList(contracts);
    } catch (Exception e) {
      logger.error("Error occurred during Contract mapping", e);
      throw new RuntimeException(e);
    }
    return contractsList;
  }

  private ContractDetails mapContractDetails(StringBuilder build) {
    ObjectMapper mapper = (ObjectMapper) context.getBean("objectMapper");
    ContractDetails contractDetails;
    try {
      contractDetails = mapper.readValue(build.toString(), ContractDetails.class);

    } catch (Exception e) {
      logger.error("Error occurred during ContractDetails mapping", e);
      throw new RuntimeException(e);
    }
    return contractDetails;
  }

  private void insertContractsList(List<Contracts> contractsList) {
    baseDbClient.insertContracts(contractsList);
  }
}
