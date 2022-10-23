package com.base.base.service;

import com.base.base.models.Contract;
import com.base.base.models.contractdetails.ContractDetails;
import com.base.base.repository.ContractDetailsRepository;
import com.base.base.repository.ContractsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BaseServiceImpl implements BaseService {

	@Autowired
	ContractsRepository contractsRepository;

	@Autowired
	ContractDetailsRepository contractDetailsRepository;

	@Override
	public List<Contract> getAllContracts() {
		return StreamSupport.stream(contractsRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public List<ContractDetails> getContractsByContractedNif(String nif) {
		var maybeListContractDetails = contractDetailsRepository.findByContracted_Nif(nif);
		return maybeListContractDetails.orElseThrow(
				() -> new IllegalArgumentException("Couldn't find Contracted Nif - " + nif));
	}
}
