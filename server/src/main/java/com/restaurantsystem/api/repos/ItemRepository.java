package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

}
