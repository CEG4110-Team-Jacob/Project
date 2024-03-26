package com.restaurantsystem.api.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Table;

public interface TableRepository extends CrudRepository<Table, Integer> {
    public <T> List<T> findAllBy(Class<T> type);
}
