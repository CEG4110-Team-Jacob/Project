package org.example.Data.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.Data.controllers.Waiters.WaiterOrder.ListOrders;
import org.example.Data.controllers.Waiters.WaiterTable.ListTables;
import org.example.Data.enums.Status;
import org.example.Data.records.Item;
import org.example.Data.utils.GetMethods;
import org.example.Data.utils.PostMethods;

public class Waiters {
    public record WaiterOrder(int id, List<Item> items, Date timeOrdered, Status status, int totalPrice, Table table) {
        public record ListOrders(List<WaiterOrder> orders) {
        };

        public record Table(int id) {
        }
    }

    public record WaiterPostOrder(List<Integer> items, int tableId) {
    }

    public record WaiterTable(
            int id,
            int number, WaiterDetails waiter, boolean isOccupied, int numSeats, int x, int y) {
        public record WaiterDetails(String firstName, String lastName, int id) {
        }

        public record ListTables(List<WaiterTable> tables) {
        }
    }

    private static GetMethods<WaiterOrder.ListOrders> orders = new GetMethods<>("/waiter/order",
            WaiterOrder.ListOrders.class);
    private static GetMethods<WaiterTable.ListTables> tables = new GetMethods<>("/waiter/tables",
            WaiterTable.ListTables.class);
    private static PostMethods<WaiterPostOrder, Integer> addOrder = new PostMethods<>("/waiter/addOrder",
            Integer.class);
    private static PostMethods<Integer, Boolean> completeOrder = new PostMethods<>("/waiter/completeOrder",
            Boolean.class);
    private static PostMethods<Integer, Boolean> cancelOrder = new PostMethods<>("/waiter/cancelOrder",
            Boolean.class);
    private static PostMethods<Integer, Boolean> orderDone = new PostMethods<>("/waiter/orderDone", Boolean.class);

    public static Optional<Boolean> orderDone(Integer body) {
        return orderDone.post(body);
    }

    public static Optional<Integer> addOrder(WaiterPostOrder body) {
        return addOrder.post(body);
    }

    public static void reset() {
        orders.reset();
    }

    public static Optional<ListOrders> getOrders() {
        return orders.set();
    }

    public static Optional<Boolean> completeOrder(Integer body) {
        return completeOrder.post(body);
    }

    public static Optional<Boolean> cancelOrder(Integer body) {
        return cancelOrder.post(body);
    }

    public static Optional<ListTables> getTables() {
        return tables.set();
    }

}
