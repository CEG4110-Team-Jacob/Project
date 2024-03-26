package org.example.Data.controllers;

import java.util.List;
import java.util.Optional;

import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;
import org.example.Data.enums.ItemType;
import org.example.Data.enums.Job;
import org.example.Data.records.Item;
import org.example.Data.records.Item.ListItems;

public class Managers {
    public record PostCreateWorker(String firstName, String lastName, int age, Job job, String username,
            String password, List<Integer> tables) {
    }

    public record PostAddItem(String name, String description, int price, boolean inStock, ItemType type) {
    }

    private static GetMethods<Item.ListItems> items = new GetMethods<>("/manager/items", Item.ListItems.class);
    private static PostMethods<PostCreateWorker, Void> createWorker = new PostMethods<>("/manager/createWorker",
            Void.class);
    private static PostMethods<PostAddItem, Void> addItem = new PostMethods<>("/manager/addItem", Void.class);

    public static Optional<ListItems> setItems() {
        return items.set();
    }

    public static Optional<ListItems> getItems() {
        return items.get();
    }

    public static Optional<Void> createWorker(PostCreateWorker body) {
        return createWorker.post(body);
    }

    public Optional<Void> addItem(PostAddItem body) {
        return addItem.post(body);
    }

}
