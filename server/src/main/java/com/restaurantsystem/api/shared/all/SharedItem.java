package com.restaurantsystem.api.shared.all;

import com.restaurantsystem.api.data.Item.ItemType;

public record SharedItem(String name, String description, ItemType type, int price, boolean inStock) {

}
