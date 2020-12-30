package com.base.base.client;

import com.base.base.models.Contracts;
import java.util.List;

public interface BaseDbClient {

  int insertContracts(List<Contracts> contractsList);

  List<Contracts> getListofIdsNotInContractDetails();
}
