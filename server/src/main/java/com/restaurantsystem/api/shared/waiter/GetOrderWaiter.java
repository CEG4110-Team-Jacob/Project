package com.restaurantsystem.api.shared.waiter;

import java.util.List;

import com.restaurantsystem.api.shared.all.Item;
import com.restaurantsystem.api.shared.enums.Status;

/**
 * OrderWaiter
 */
public record GetOrderWaiter(int id, List<Item> items, int timeOrdered,
        Status status, int totalPrice) {
}
