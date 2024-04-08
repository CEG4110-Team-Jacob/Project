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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.controllers.CookController.PostSetStatus;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.shared.TestSharedItem;

/**
 * Tests for CookController's APIs
 */
@Transactional
@Rollback(true)
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
    void setStatus() throws Exception {
        postMockMvcBuilder("/setStatus", toJson(new PostSetStatus(Status.Completed, 1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        postMockMvcResult("/setStatus", toJson(new PostSetStatus(Status.InProgress, 1)));
        Optional<Order> order = orderRepository.findById(1);
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.InProgress);
        postMockMvcResult("/setStatus", toJson(new PostSetStatus(Status.Cooked, 2)));
        Optional<Order> order2 = orderRepository.findById(2);
        assertTrue(order2.isPresent());
        assertEquals(order2.get().getStatus(), Status.Cooked);
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
