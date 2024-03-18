package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Table;

public interface TableRepository extends CrudRepository<Table, Integer> {

}
