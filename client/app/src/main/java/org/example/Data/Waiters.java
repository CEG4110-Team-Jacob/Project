package org.example.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.Data.enums.Status;
import org.example.Data.records.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Waiters {
    public record WaiterOrder(int id, List<Item> items, Date timeOrdered, Status status, int totalPrice) {
        public record ListOrders(List<WaiterOrder> orders) {
        };
    }

    private static List<WaiterOrder> orders = new ArrayList<>();

    public static void reset() {
        orders = new ArrayList<>();
    }

    public static Optional<List<WaiterOrder>> updateOrders() {
        String query = "t=" + Data.token;
        try {
            ResponseEntity<WaiterOrder.ListOrders> ordersResponse = General.restClient.get()
                    .uri(General.URI + "/waiter/order?" + query).retrieve().toEntity(WaiterOrder.ListOrders.class);
            if (ordersResponse.getStatusCode() != HttpStatus.OK)
                return Optional.empty();
            orders = ordersResponse.getBody().orders;
            return Optional.of(ordersResponse.getBody().orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<List<WaiterOrder>> getOrders() {
        if (!orders.isEmpty())
            return Optional.of(orders);
        return updateOrders();
    }
}
