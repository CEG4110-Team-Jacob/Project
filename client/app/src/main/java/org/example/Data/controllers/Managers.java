package org.example.Data.controllers;

import java.util.List;
import java.util.Optional;

import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;
import org.example.Data.controllers.Managers.ManagerViewWorker.ListWorkers;
import org.example.Data.enums.ItemType;
import org.example.Data.enums.Job;
import org.example.Data.records.Item;
import org.example.Data.records.Item.ListItems;

public class Managers {
    public record PostCreateWorker(String firstName, String lastName, int age, Job job, String username,
            String password) {
    }

    public record PostAddItem(String name, String description, int price, boolean inStock, ItemType type) {
    }

    public record ManagerViewWorker(
            String firstName, String lastName,
            int id, int age, Job job) {
        public record ListWorkers(List<ManagerViewWorker> workers) {
        };
    }

    private static GetMethods<Item.ListItems> items = new GetMethods<>("/manager/items", Item.ListItems.class);
    private static PostMethods<PostCreateWorker, Boolean> createWorker = new PostMethods<>("/manager/createWorker",
            Boolean.class);

    private static PostMethods<PostAddItem, Boolean> addItem = new PostMethods<>("/manager/addItem", Boolean.class);

    private static GetMethods<ManagerViewWorker.ListWorkers> getWorkers = new GetMethods<>("/manager/workers",
            ManagerViewWorker.ListWorkers.class);

    public static Optional<ListItems> setItems() {
        return items.set();
    }

    public static Optional<ListItems> getItems() {
        return items.get();
    }

    public static Optional<Boolean> createWorker(PostCreateWorker body) {
        return createWorker.post(body);
    }

    public static Optional<Boolean> addItem(PostAddItem body) {
        return addItem.post(body);
    }

    public static Optional<ListWorkers> setWorkers() {
        return getWorkers.set();
    }

    public static Optional<ListWorkers> getWorkers() {
        return getWorkers.get();
    }

}
