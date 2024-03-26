package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.TestSharedItem;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
public class CookControllerTests extends ControllerParentTests {
    record Orders(List<Order> orders) {
        record Order(List<TestSharedItem> items, Date timeOrdered, Status status, int id) {
        }
    }

    public CookControllerTests() {
        login = DatabasePopulate.Cook1;
        path = "cook";
    }

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void contextLoads() {
        assertNotNull(authenticationService);
        assertNotNull(restTemplate);
    }

    @Test
    void getOrders() {
        ResponseEntity<Orders> orders = restTemplate.getForEntity(getUrl() + "getOrders?t=" + token, Orders.class);
        assertEquals(orders.getStatusCode(), HttpStatus.OK);
        assertTrue(orders.getBody().orders.size() > 1);
    }

    @Test
    void cookingOrder() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() +
                "cookingOrder?t=" + token, 1,
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Optional<Order> order = orderRepository.findById(1);
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.InProgress);
        order.get().setStatus(Status.Ordered);
        orderRepository.save(order.get());

        ResponseEntity<String> fail = restTemplate.postForEntity(getUrl() +
                "cookingOrder?t=" + token, 2,
                String.class);
        assertEquals(fail.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void completeOrder() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() +
                "completeOrder?t=" + token, 2,
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Optional<Order> order = orderRepository.findById(2);
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.Cooked);
        order.get().setStatus(Status.InProgress);
        orderRepository.save(order.get());

        ResponseEntity<String> fail = restTemplate.postForEntity(getUrl() +
                "completeOrder?t=" + token, 1,
                String.class);
        assertEquals(fail.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getItems() {
        ResponseEntity<ListOfItems> items = restTemplate.getForEntity(getUrl() + "items?t=" + token,
                ListOfItems.class);
        assertEquals(items.getStatusCode(), HttpStatus.OK);
        assertTrue(items.getBody().items().size() > 0);
    }

    @Autowired
    ItemRepository itemRepository;

    @Test
    void itemsDepleted() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() + "itemDepleted?t=" + token, 1,
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(itemRepository.findById(1).isPresent());
        assertFalse(itemRepository.findById(1).get().isInStock());
        itemRestocked();
    }

    void itemRestocked() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() + "itemRestocked?t=" + token, 1,
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(itemRepository.findById(1).isPresent());
        assertTrue(itemRepository.findById(1).get().isInStock());
    }
}
