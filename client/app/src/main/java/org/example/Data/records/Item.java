package org.example.Data.records;

import java.util.List;

import org.example.Data.enums.ItemType;

public record Item(int id, String name, ItemType type, int price, boolean inStock, String description) {
    public record ListItems(List<Item> items) {
    };
}
