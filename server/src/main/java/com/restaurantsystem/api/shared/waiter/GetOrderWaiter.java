package com.restaurantsystem.api.shared.waiter;

import java.util.List;

import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.shared.all.SharedItem;

public interface GetOrderWaiter {
    int getId();

    List<SharedItem> getItems();

    long getTimeOrdered();

    Status getStatus();

    int getTotalPrice();
}