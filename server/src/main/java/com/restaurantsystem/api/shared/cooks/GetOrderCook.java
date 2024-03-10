package com.restaurantsystem.api.shared.cooks;

import java.util.List;

import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.shared.all.SharedItem;

public record GetOrderCook(List<SharedItem> items, int timePlaced, Status status) {

}
