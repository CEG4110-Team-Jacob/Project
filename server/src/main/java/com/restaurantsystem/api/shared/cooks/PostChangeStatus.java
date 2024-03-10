package com.restaurantsystem.api.shared.cooks;

public record PostChangeStatus(int orderId, com.restaurantsystem.api.data.Order.Status status) {

}
