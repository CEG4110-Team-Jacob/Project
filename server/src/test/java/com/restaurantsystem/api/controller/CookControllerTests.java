package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.TestSharedItem;

public class CookControllerTests extends ControllerParentTests {
    record Orders(List<Order> orders) {
        record Order(List<TestSharedItem> items, Date timeOrdered, Status status, int id) {
        }
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    public CookControllerTests() {
        login = DatabasePopulate.Cook1;
        path = "/cook";
    }

    @Test
    void contextLoads() {
        assertNotNull(authenticationService);
    }

    @Test
    void getOrders() throws Exception {
        var orders = getMockMvcResultType("/getOrders", Orders.class);
        assertTrue(orders.orders.size() > 1);
    }

    @Test
    void cookingOrder() throws Exception {
        postMockMvcResult("/cookingOrder", "1");
        Optional<Order> order = orderRepository.findById(1);
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.InProgress);
        order.get().setStatus(Status.Ordered);
        orderRepository.save(order.get());

        postMockMvcBuilder("/cookingOrder", "2").andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void completeOrder() throws Exception {
        postMockMvcResult("/completeOrder", "2");
        Optional<Order> order = orderRepository.findById(2);
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.Cooked);
        order.get().setStatus(Status.InProgress);
        orderRepository.save(order.get());

        postMockMvcBuilder("/completeOrder", "1").andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getItems() throws Exception {
        var items = getMockMvcResultType("/items", ListOfItems.class);
        assertTrue(items.items().size() > 0);
    }

    @Test
    void itemsDepleted() throws Exception {
        postMockMvcResult("/itemDepleted", "1");
        assertTrue(itemRepository.findById(1).isPresent());
        assertFalse(itemRepository.findById(1).get().isInStock());
        itemRestocked();
    }

    void itemRestocked() throws Exception {
        postMockMvcResult("/itemRestocked", "1");
        assertTrue(itemRepository.findById(1).isPresent());
        assertTrue(itemRepository.findById(1).get().isInStock());
    }
}
