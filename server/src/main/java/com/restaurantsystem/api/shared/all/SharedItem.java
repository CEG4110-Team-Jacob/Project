package com.restaurantsystem.api.shared.all;

import com.restaurantsystem.api.data.Item.ItemType;

public interface SharedItem {
    int getId();

    String getName();

    String getDescription();

    ItemType getType();

    int getPrice();

    boolean isInStock();

}