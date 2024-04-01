package com.restaurantsystem.api.shared.waiter;

import java.util.List;
import java.util.Date;

import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.shared.all.SharedItem;

public interface GetOrderWaiter {
    public interface Table {
        int getId();
    }

    Table getTable();

    int getId();

    List<SharedItem> getItems();

    Date getTimeOrdered();

    Status getStatus();

    int getTotalPrice();
}