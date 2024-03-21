package org.example.Data;

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

    public static Optional<WaiterOrder.ListOrders> getOrders() {
        String query = "t=" + Data.token;
        try {
            ResponseEntity<WaiterOrder.ListOrders> ordersResponse = HttpUtils.restClient.get()
                    .uri(HttpUtils.URI + "/waiter/order?" + query).retrieve().toEntity(WaiterOrder.ListOrders.class);
            if (ordersResponse.getStatusCode() != HttpStatus.OK)
                return Optional.empty();
            return Optional.of(ordersResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
