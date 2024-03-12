package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurantsystem.api.data.Worker;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer> {

}
