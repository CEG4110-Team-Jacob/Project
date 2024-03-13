package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.restaurantsystem.api.data.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    <T> Optional<T> findById(Integer id, Class<T> type);
}
