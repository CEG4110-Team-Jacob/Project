package org.example.Data.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;
import org.example.Data.controllers.Waiters.WaiterOrder.ListOrders;
import org.example.Data.controllers.Waiters.WaiterTable.ListTables;
import org.example.Data.enums.Status;
import org.example.Data.records.Item;
import org.example.Data.records.Item.ListItems;

public class Waiters {
    public record WaiterOrder(int id, List<Item> items, Date timeOrdered, Status status, int totalPrice) {
        public record ListOrders(List<WaiterOrder> orders) {
        };
    }

    public record WaiterPostOrder(List<Integer> items, int table) {
    }

    public record WaiterTable(
            int id,
            int number, WaiterDetails waiter, boolean isOccupied, int numSeats) {
        public record WaiterDetails(String firstName, String lastName) {
        }

        public record ListTables(List<WaiterTable> tables) {
        }
    }

    private static GetMethods<WaiterOrder.ListOrders> orders = new GetMethods<>("/waiter/order",
            WaiterOrder.ListOrders.class);
    private static GetMethods<Item.ListItems> items = new GetMethods<>("/waiter/items", Item.ListItems.class);
    private static GetMethods<WaiterTable.ListTables> tables = new GetMethods<>("/waiter/tables",
            WaiterTable.ListTables.class);
    private static PostMethods<WaiterPostOrder, Integer> addOrder = new PostMethods<>("/waiter/addOrder",
            Integer.class);
    private static PostMethods<Integer, Boolean> completeOrder = new PostMethods<>("/water/completeOrder",
            Boolean.class);
    private static PostMethods<Integer, Boolean> cancelOrder = new PostMethods<>("/water/cancelOrder",
            Boolean.class);

    public static Optional<Integer> addOrder(WaiterPostOrder body) {
        return addOrder.post(body);
    }

    public static Optional<ListItems> setItems() {
        return items.set();
    }

    public static Optional<ListItems> getItems() {
        return items.get();
    }

    public static void reset() {
        orders.reset();
        items.reset();
    }

    public static Optional<ListOrders> setOrders() {
        return orders.set();
    }

    public static Optional<ListOrders> getOrders() {
        return orders.get();
    }

    public static Optional<Boolean> completeOrder(Integer body) {
        return completeOrder.post(body);
    }

    public static Optional<Boolean> cancelOrder(Integer body) {
        return cancelOrder.post(body);
    }

    public static Optional<ListTables> setTables() {
        return tables.set();
    }

    public static Optional<ListTables> getTables() {
        return tables.get();
    }

}
