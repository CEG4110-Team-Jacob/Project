package com.restaurantsystem.api.shared.manager;

import com.restaurantsystem.api.data.Item.ItemType;

public record AddItem(String name, String description, int price, boolean inStock, ItemType type) {

}
