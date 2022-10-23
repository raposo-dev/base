package com.base.base.repository;

import com.base.base.models.contractdetails.ContractDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractDetailsRepository extends CrudRepository<ContractDetails, Integer> {
	@Query("select c from ContractDetails c inner join c.contracted contracted where contracted.nif = ?1")
	Optional<List<ContractDetails>> findByContracted_Nif(String nif);
}
