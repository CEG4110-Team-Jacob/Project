package com.restaurantsystem.api.shared;

import com.restaurantsystem.api.data.Item.ItemType;

public record TestSharedItem(String description, int id, String name, ItemType type, int price, boolean inStock) {

}
