package com.restaurantsystem.api.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurantsystem.api.data.Worker;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer> {
    public Optional<Worker> findByUsername(String username);

    public Optional<Worker> findByToken(String token);
}
