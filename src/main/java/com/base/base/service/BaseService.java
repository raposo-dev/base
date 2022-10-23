package com.base.base.service;

import com.base.base.models.Contract;
import com.base.base.models.contractdetails.ContractDetails;

import java.util.Date;
import java.util.List;

public interface BaseService {

	List<Contract> getAllContracts();

	List<ContractDetails> getContractDetailsByContractedNif(String nif);

	List<ContractDetails> getContractDetailsByPublicationDate(Date date);

}
