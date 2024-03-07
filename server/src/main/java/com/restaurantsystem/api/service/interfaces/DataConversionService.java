package com.restaurantsystem.api.service.interfaces;

import java.util.List;

import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.shared.all.Item;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;

public interface DataConversionService {
    public List<Item> toSharedItems(List<com.restaurantsystem.api.data.Item> items);

    public Item toSharedItem(com.restaurantsystem.api.data.Item item);

    public GetOrderWaiter toSharedOrder(Order order);
}
