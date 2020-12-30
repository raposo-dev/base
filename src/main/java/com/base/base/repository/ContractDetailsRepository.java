package com.base.base.repository;

import com.base.base.models.contractdetails.ContractDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDetailsRepository extends CrudRepository<ContractDetails, Integer> {

}
