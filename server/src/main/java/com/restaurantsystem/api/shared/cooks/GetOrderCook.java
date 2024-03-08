package com.restaurantsystem.api.shared.cooks;

import java.util.List;

import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.data.enums.Status;

public record GetOrderCook(List<SharedItem> items, int timePlaced, Status status) {

}
