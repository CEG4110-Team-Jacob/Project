package com.restaurantsystem.api.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurantsystem.api.data.Worker;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer> {
    public Optional<Worker> findByUsername(String username);

    public Optional<Worker> findByToken(String token);

    public <T> Optional<T> findById(int id, Class<T> type);

    public <T> List<T> findAllBy(Class<T> type);

    public <T> List<T> findAllByIsActive(boolean isActive, Class<T> type);

    public boolean existsByUsername(String username);

}
