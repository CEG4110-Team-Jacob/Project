package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
