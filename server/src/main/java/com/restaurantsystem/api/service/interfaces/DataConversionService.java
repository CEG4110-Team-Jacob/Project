package com.restaurantsystem.api.service.interfaces;

import java.util.List;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

public interface DataConversionService {
    public List<SharedItem> toSharedItems(List<Item> items);

    public SharedItem toSharedItem(Item item);

    public GetOrderWaiter toSharedOrder(Order order);

    public Order toOrder(PostOrderWaiter order, int waiterId);
}
