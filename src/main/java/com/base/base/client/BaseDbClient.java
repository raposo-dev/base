package com.base.base.client;

import com.base.base.models.Contract;

import java.util.List;

public interface BaseDbClient {

	void insertContracts(List<Contract> contractList);

	List<Contract> getListofIdsNotInContractDetails();
}
