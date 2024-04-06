package com.restaurantsystem.api.integrations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.controllers.CookController.PostSetStatus;
import com.restaurantsystem.api.data.Order.Status;

@Rollback(true)
@Transactional
public class CookIntegrations extends BaseIntegrationTests {

    @Test
    void setOrderStatus() {
        assertTrue(cookController.setOrderStatus(cookT, new PostSetStatus(Status.Cooked, 2)).getStatusCode()
                .is2xxSuccessful());

        var waiterOrders = waiterController.getOrder(waiterT);
        assertTrue(waiterOrders.getStatusCode().is2xxSuccessful());
        var waiterOrder = waiterOrders.getBody().orders().stream().filter(order -> order.getId() == 2).findAny();
        assertTrue(waiterOrder.isPresent());
        assertTrue(waiterOrder.get().getStatus() == Status.Cooked);
    }

    @Test
    void setItemStock() {
        assertTrue(cookController.itemRestocked(cookT, 3).getStatusCode().is2xxSuccessful());

        var items = mainController.getItems(waiterT);
        assertTrue(items.getStatusCode().is2xxSuccessful());
        var foundItem = items.getBody().items().stream().filter(item -> item.getId() == 3).findAny();
        assertTrue(foundItem.isPresent());
        assertTrue(foundItem.get().isInStock());
    }
}
