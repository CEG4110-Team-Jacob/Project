package com.restaurantsystem.api.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
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
    void setOrderStatusWS() throws Exception {
        client.setTaskScheduler(new SimpleAsyncTaskScheduler());
        StompSession stompSession = client.connectAsync(getWSUrl(), new StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>();

        stompSession.subscribe("/topic/order/1", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                blockingQueue.offer(Integer.parseInt((String) payload));
            }
        });
        cookController.setOrderStatus(cookT, new PostSetStatus(Status.Cooked, 2));
        var returnValue = blockingQueue.poll(10, TimeUnit.SECONDS);
        System.out.println(returnValue);
        assertEquals(returnValue, 2);
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
