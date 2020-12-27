package com.basetwitter.basetwitter.client;

import com.basetwitter.basetwitter.models.Contracts;
import java.util.List;

public interface BaseDbClient {

  int insertContracts(List<Contracts> contractsList);

  List<Contracts> getListofIdsNotInContractDetails();
}
