package com.restaurantsystem.api.shared.waiter;

import java.util.List;

import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.shared.all.SharedItem;

/**
 * OrderWaiter
 */
public record GetOrderWaiter(int id, List<SharedItem> items, long timeOrdered,
                Status status, int totalPrice) {
}
