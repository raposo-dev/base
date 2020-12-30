package com.base.base.repository;

import com.base.base.models.Contracts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends CrudRepository<Contracts, Integer> {}
