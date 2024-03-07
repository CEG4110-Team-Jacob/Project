package com.restaurantsystem.api.shared.all;

import com.restaurantsystem.api.shared.enums.ItemType;

public record Item(String name, String description, ItemType type, int price, boolean inStock) {

}
