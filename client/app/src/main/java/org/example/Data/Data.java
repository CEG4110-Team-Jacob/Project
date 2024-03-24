package org.example.Data;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Data {
    public record Order(int id, String status, List<String> items) {
    }

    public record Worker(int id, String name, List<Integer> tables, long login) {
    }

    public record Table(int id, Order order) {
    }

    private static HashMap<Integer, Order> orders = new HashMap<>();

    private static HashMap<Integer, Worker> workers = new HashMap<>();

    private static HashMap<Integer, Table> tables = new HashMap<>();

    public static void deleteData() {
        Waiters.reset();
        General.reset();
    }

    static {
        orders.put(1, new Order(1, "AS", Arrays.asList("Burger")));
        orders.put(2, new Order(2, "AS", Arrays.asList("Burger", "Fries")));
        orders.put(10, new Order(10, "L", Arrays.asList("Water", "Fries")));
        workers.put(1, new Worker(1, "Worker 1", Arrays.asList(1, 2), Instant.now().getEpochSecond() - 60 * 60));
        workers.put(2, new Worker(1, "Worker 2", Arrays.asList(4, 3), Instant.now().getEpochSecond() - 60 * 60 * 2));
        tables.put(1, new Table(1, orders.get(1)));
        tables.put(2, new Table(2, orders.get(2)));
        tables.put(3, new Table(3, orders.get(10)));
        tables.put(4, new Table(4, orders.get(1)));
    };

    public static HashMap<Integer, Table> getTables() {
        return tables;
    };

    public static HashMap<Integer, Worker> getWorkers() {
        return workers;
    }

    public static HashMap<Integer, Order> getOrders() {
        return orders;
    }

}
