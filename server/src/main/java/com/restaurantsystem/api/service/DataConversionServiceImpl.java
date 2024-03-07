package com.restaurantsystem.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.service.interfaces.DataConversionService;
import com.restaurantsystem.api.shared.all.Item;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;

@Service
public class DataConversionServiceImpl implements DataConversionService {

    @Override
    public List<Item> toSharedItems(List<com.restaurantsystem.api.data.Item> items) {
        if (items == null)
            return new ArrayList<>();
        List<Item> converted = new ArrayList<>(items.size());
        for (com.restaurantsystem.api.data.Item item : items) {
            converted.add(toSharedItem(item));
        }
        return converted;
    }

    @Override
    public Item toSharedItem(com.restaurantsystem.api.data.Item item) {
        return new Item(item.getName(), item.getDescription(), item.getType(), item.getPrice(), item.isInStock());
    }

    @Override
    public GetOrderWaiter toSharedOrder(Order order) {
        return new GetOrderWaiter(order.getId(), toSharedItems(order.getItems()), order.getTimeOrdered(),
                order.getStatus(), order.getTotalPrice());
    }

}
