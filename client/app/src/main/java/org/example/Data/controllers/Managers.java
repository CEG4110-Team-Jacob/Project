package org.example.Data.controllers;

import java.util.List;
import java.util.Optional;

import org.example.Data.controllers.Managers.ManagerViewWorker.ListWorkers;
import org.example.Data.enums.ItemType;
import org.example.Data.enums.Job;
import org.example.Data.utils.GetMethods;
import org.example.Data.utils.PostMethods;

public class Managers {
    public record PostCreateWorker(String firstName, String lastName, int age, Job job, String username,
            String password) {
    }

    public record PostAddItem(String name, String description, int price, boolean inStock, ItemType type) {
    }

    /**
     * PostChangeItem
     */
    public record PostChangeItem(int id, PostAddItem details) {
    }

    public record ManagerViewWorker(
            String firstName, String lastName,
            int id, int age, Job job) {
        public record ListWorkers(List<ManagerViewWorker> workers) {
        };
    }

    public record PostSetTable(int x, int y, int waiter, int number, int numSeats, boolean isOccupied,
            boolean isActive) {
    }

    private static PostMethods<PostCreateWorker, Boolean> createWorker = new PostMethods<>("/manager/createWorker",
            Boolean.class);

    private static PostMethods<PostAddItem, Boolean> addItem = new PostMethods<>("/manager/addItem", Boolean.class);

    private static GetMethods<ManagerViewWorker.ListWorkers> getWorkers = new GetMethods<>("/manager/workers",
            ManagerViewWorker.ListWorkers.class);

    private static PostMethods<Integer, Boolean> deleteWorker = new PostMethods<>("/manager/deleteWorker",
            Boolean.class);

    private static PostMethods<PostChangeItem, Boolean> changeItem = new PostMethods<>("/manager/changeItem",
            Boolean.class);

    private static PostMethods<Integer, Boolean> deleteItem = new PostMethods<>("/manager/deleteItem", Boolean.class);

    private static PostMethods<PostSetTable, Boolean> setTable = new PostMethods<>("/manager/setTable", Boolean.class);

    public static Optional<Boolean> setTable(PostSetTable body) {
        return setTable.post(body);
    }

    public static Optional<Boolean> deleteItem(Integer body) {
        return deleteItem.post(body);
    }

    public static Optional<Boolean> changeItem(PostChangeItem body) {
        return changeItem.post(body);
    }

    public static void reset() {
        getWorkers.reset();
    }

    public static Optional<Boolean> deleteWorker(Integer body) {
        return deleteWorker.post(body);
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
        return getWorkers.set();
    }

}
