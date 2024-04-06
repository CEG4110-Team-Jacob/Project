package com.restaurantsystem.api.integrations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

@Rollback(true)
@Transactional
public class WaiterIntegrations extends BaseIntegrationTests {
    // Tests the POST request for Waiters to all others
    @Test
    void addOrder() {
        var waiterOrder = new PostOrderWaiter(Arrays.asList(1, 2), 1);
        ResponseEntity<Integer> orderResponse = waiterController.addOrder(waiterOrder, waiterT);
        assertTrue(orderResponse.getStatusCode().is2xxSuccessful());
        // 2 active orders cannot be on a table
        assertTrue(waiterController.addOrder(waiterOrder, waiterT).getStatusCode().isError());

        var cooksOrderResponse = cookController.getOrders(cookT);
        assertTrue(cooksOrderResponse.getStatusCode().is2xxSuccessful());
        var cooksOrder = cooksOrderResponse.getBody();
        // Check if the order added is seen by cooks
        assertTrue(cooksOrder.orders().stream().filter(order -> order.getId() == orderResponse.getBody()).findAny()
                .isPresent());

        var hostTables = hostController.getTables(hostT);
        assertTrue(hostTables.getStatusCode().is2xxSuccessful());
        // The table of the order
        var hostTable = hostTables.getBody().tables().stream().filter(table -> table.getId() == 1).findAny();
        assertTrue(hostTable.isPresent());
        // Make sure it's occupied
        assertTrue(hostTable.get().getIsOccupied());
    }

    @Test
    void changeOrderStatus() {
        assertTrue(waiterController.completeOrder(3, waiterT).getStatusCode().is2xxSuccessful());
        var cooksOrders = cookController.getOrders(cookT);
        assertTrue(cooksOrders.getStatusCode().is2xxSuccessful());
        // Cook cannot seen a delivered order
        var cookOrder = cooksOrders.getBody().orders().stream().filter(order -> order.getId() == 3).findAny();
        assertTrue(cookOrder.isEmpty());
        // Check waiter view
        var waiterOrders = waiterController.getOrder(waiterT);
        assertTrue(waiterOrders.getStatusCode().is2xxSuccessful());
        var waiterOrder = waiterOrders.getBody().orders().stream().filter(order -> order.getId() == 3).findAny();
        assertTrue(waiterOrder.isPresent());
        assertTrue(waiterOrder.get().getStatus() == Status.Delivered);
    }
}
