package com.basetwitter.basetwitter.repository;

import com.basetwitter.basetwitter.models.Contracts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends CrudRepository<Contracts, Integer> {}
