package com.restaurantsystem.api.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Table;

public interface TableRepository extends CrudRepository<Table, Integer> {
    public <T> List<T> findAllByIsActive(boolean isActive, Class<T> type);

    public Optional<Table> findByXAndY(int x, int y);

}
