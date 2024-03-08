package com.restaurantsystem.api.service.interfaces;

import java.util.List;

import com.restaurantsystem.api.shared.cooks.GetOrderCook;
import com.restaurantsystem.api.shared.cooks.PostChangeStatus;

public interface CookService {
    public List<GetOrderCook> getOrders();

    public GetOrderCook getOrder(int orderId);

    public void changeStatus(PostChangeStatus data);
}
