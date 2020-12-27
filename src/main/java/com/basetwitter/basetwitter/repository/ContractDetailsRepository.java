package com.basetwitter.basetwitter.repository;

import com.basetwitter.basetwitter.models.contractdetails.ContractDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDetailsRepository extends CrudRepository<ContractDetails, Integer> {

}
