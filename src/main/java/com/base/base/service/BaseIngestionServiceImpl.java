package com.base.base.service;

import com.base.base.ApplicationConfig;
import com.base.base.client.BaseDbClient;
import com.base.base.client.BaseHttpClient;
import com.base.base.models.Contract;
import com.base.base.models.contractdetails.ContractDetails;
import com.base.base.repository.ContractDetailsRepository;
import com.base.base.repository.ContractsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class BaseIngestionServiceImpl implements BaseIngestionService {
	private static final int NUMBER_OF_STEPS = -1000;

	private static final Logger logger = LoggerFactory.getLogger(BaseIngestionService.class);
	final BaseDbClient baseDbClient;
	final ContractDetailsRepository contractDetailsRepository;
	final BaseHttpClient baseHttpClient;
	final ContractsRepository contractsRepository;
	ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

	@Value("${base.url.results}")
	String baseUrlResults;

	@Value("${base.url.contracts}")
	String baseUrlContracts;

	public BaseIngestionServiceImpl(
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
		List<Contract> contractList;
		Integer numberOfContracts = getTotalContracts();
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
			contractList = mapContractsList(build);
			insertContractsList(contractList);
		}
	}

	@Override
	public void insertContractDetails() {
		List<Contract> contractList = baseDbClient.getListofIdsNotInContractDetails();
		logger.info("Getting Contract Details for {} contracts.", contractList.size());
		contractList.stream()
				.map(this::getBaseContractDetails)
				.forEach(contractDetailsRepository::save);
	}

	private int getTotalContracts() throws IOException {
		return baseHttpClient.getNumberOfContracts("https://www.base.gov.pt/Base4/pt/resultados/");
	}

	private ContractDetails getBaseContractDetails(Contract contract) {

		try {
			URL url = new URL(baseUrlContracts + "/" + contract.getId());
			BufferedReader resp = baseHttpClient.getBaseResponseBufferedReader(url);
			StringBuilder build = new StringBuilder();
			String inputLine;

			while ((inputLine = resp.readLine()) != null) {
				build.append(inputLine);
			}
			ContractDetails contractDetails = mapContractDetails(build);
			if (contractDetails.getId() == 0) {
				contractDetails.setId(contract.getId());
			}

			return contractDetails;
		} catch (IOException e) {
			logger.error("Error occurred during request process", e);
			throw new RuntimeException(e);
		}

	}

	private List<Contract> mapContractsList(StringBuilder build) {
		ObjectMapper mapper = (ObjectMapper) context.getBean("objectMapper");
		List<Contract> contractList;
		try {
			Contract[] contracts = mapper.readValue(build.toString(), Contract[].class);
			contractList = Arrays.asList(contracts);
		} catch (Exception e) {
			logger.error("Error occurred during Contract mapping", e);
			throw new RuntimeException(e);
		}
		return contractList;
	}

	private ContractDetails mapContractDetails(StringBuilder build) {
		ObjectMapper mapper = (ObjectMapper) context.getBean("objectMapper");
		ContractDetails contractDetails;
		try {
			logger.info(build.toString());
			contractDetails = mapper.readValue(build.toString(), ContractDetails.class);
		} catch (Exception e) {
			logger.error("Error occurred during ContractDetails mapping", e);
			throw new RuntimeException(e);
		}
		return contractDetails;
	}

	private void insertContractsList(List<Contract> contractList) {
		baseDbClient.insertContracts(contractList);
	}
}
