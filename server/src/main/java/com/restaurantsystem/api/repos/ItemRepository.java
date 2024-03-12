package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurantsystem.api.data.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

}
