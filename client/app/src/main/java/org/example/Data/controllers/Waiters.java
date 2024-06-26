package org.example.Data.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.example.Data.controllers.Waiters.WaiterOrder.ListOrders;
import org.example.Data.controllers.Waiters.WaiterTable.ListTables;
import org.example.Data.enums.Status;
import org.example.Data.records.Item;
import org.example.Data.utils.GetMethods;
import org.example.Data.utils.PostMethods;
import org.example.Data.utils.Websocket;

/**
 * Waiter's APIs
 * <a href=
 * "https://github.com/CEG4110-Team-Jacob/Project/wiki/Server#waiter">Documentation</a>
 */
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
    private static Websocket<String> orderCooked = new Websocket<>(o -> {
        try (
                Scanner scanner = new Scanner(o);) {
            var orderId = scanner.nextInt();
            var tableNumber = scanner.nextInt();
            JOptionPane.showMessageDialog(null, "Order " + orderId + " at table " + tableNumber + " Cooked.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }, String.class, "/topic/order/");

    public static void startOrderCooked() {
        System.out.println("Started");
        orderCooked.start();
    }

    public static Optional<Boolean> orderDone(Integer body) {
        return orderDone.post(body);
    }

    public static Optional<Integer> addOrder(WaiterPostOrder body) {
        return addOrder.post(body);
    }

    public static void reset() {
        orders.reset();
        tables.reset();
        orderCooked.stop();
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
