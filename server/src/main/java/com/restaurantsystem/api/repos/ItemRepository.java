package com.restaurantsystem.api.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurantsystem.api.data.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    <T> Optional<T> findById(Integer id, Class<T> type);

    <T> List<T> findAllBy(Class<T> type);

    boolean existsByName(String name);
}
