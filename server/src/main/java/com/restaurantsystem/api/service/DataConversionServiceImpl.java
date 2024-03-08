package com.restaurantsystem.api.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.enums.Status;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.service.interfaces.DataConversionService;
import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

@Service
public class DataConversionServiceImpl implements DataConversionService {
    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<SharedItem> toSharedItems(List<Item> items) {
        if (items == null)
            return new ArrayList<>();
        List<SharedItem> converted = new ArrayList<>(items.size());
        for (Item item : items) {
            converted.add(toSharedItem(item));
        }
        return converted;
    }

    @Override
    public SharedItem toSharedItem(Item item) {
        return new SharedItem(item.getName(), item.getDescription(), item.getType(), item.getPrice(), item.isInStock());
    }

    @Override
    public GetOrderWaiter toSharedOrder(Order order) {
        return new GetOrderWaiter(order.getId(), toSharedItems(order.getItems()), order.getTimeOrdered(),
                order.getStatus(), order.getTotalPrice());
    }

    List<Item> itemIdsToItems(List<Integer> ids) {
        List<Item> items = new ArrayList<>();
        for (Item i : itemRepository.findAllById(ids)) {
            items.add(i);
        }
        return items;
    }

    @Override
    public Order toOrder(PostOrderWaiter order, int waiterId) {
        Order o = new Order();
        o.setItems(itemIdsToItems(order.items()));
        o.setStatus(Status.Order);
        o.setTimeOrdered(Instant.now().getEpochSecond());
        o.setId(waiterId);
        return o;
    }

}
