package com.restaurantsystem.api.shared.waiter;

import java.util.List;

import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.enums.Status;

/**
 * OrderWaiter
 */
public record GetOrderWaiter(int id, List<SharedItem> items, long timeOrdered,
        Status status, int totalPrice) {
}
