package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Worker;

public interface WorkerRepository extends CrudRepository<Worker, Integer> {

}
