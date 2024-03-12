package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurantsystem.api.data.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
