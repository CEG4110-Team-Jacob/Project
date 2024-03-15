package com.restaurantsystem.api.shared.cooks;

import java.util.Date;
import java.util.List;

import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.shared.all.SharedItem;

public interface GetOrderCook {

    List<SharedItem> getItems();

    Date getTimeOrdered();

    Status getStatus();

    int getId();
}
