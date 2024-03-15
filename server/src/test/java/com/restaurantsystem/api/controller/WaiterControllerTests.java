package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationServiceImpl;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
public class WaiterControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private String token;

    @BeforeEach
    void login() {
        token = authenticationService.login(DatabasePopulate.Waiter1.username(), DatabasePopulate.Waiter1.password())
                .get();
    }

    @Test
    void contextLoads() {
        assertNotNull(restTemplate);
        assertNotNull(authenticationService);
    }

    record ListOfOrders(List<OrderRecord> orders) {
        record OrderRecord(int id, List<Item> items, Date timeOrdered, Status status, int totalPrice) {
        }

        record Item(String description, int id, String name, ItemType type, int price, boolean inStock) {
        };
    }

    String getUrl() {
        return "http://localhost:" + port + "/waiter/";
    }

    @Transactional
    @Test
    void getOrder() {
        ResponseEntity<ListOfOrders> response = restTemplate
                .getForEntity(getUrl() + "order?t=" + token, ListOfOrders.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().orders.isEmpty());
        Optional<String> hostToken = authenticationService.login(DatabasePopulate.Host1.username(),
                DatabasePopulate.Host1.password());
        assertTrue(hostToken.isPresent());
        ResponseEntity<ListOfOrders> hostReponse = restTemplate
                .getForEntity(getUrl() + "order?t=" + hostToken, ListOfOrders.class);
        assertEquals(hostReponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    @Test
    void addOrder() {
        ResponseEntity<Integer> response = restTemplate.postForEntity(
                getUrl() + "addOrder?t=" + token, new PostOrderWaiter(Arrays.asList(1, 2)), Integer.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody() > 0);
        Optional<Worker> waiter = workerRepository.findByToken(token);
        assertTrue(waiter.isPresent());
        Optional<Order> order = orderRepository.findById(response.getBody());
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.Ordered);
        List<Item> items = order.get().getItems();
        assertEquals(items.get(0).getId(), 1);
        assertEquals(items.get(1).getId(), 2);
        assertTrue(order.get().getTimeOrdered().before(new Date()));
        assertTrue(order.get().getTimeOrdered().after(new Date(new Date().getTime() - 10 * 1000)));
    }

    @Transactional
    @Test
    void completeOrder() {
        int cookedOrderId = 3;
        ResponseEntity<String> cookedOrder = restTemplate.postForEntity(getUrl() + "completeOrder?t=" + token,
                cookedOrderId,
                String.class);
        assertEquals(cookedOrder.getStatusCode(), HttpStatus.OK);
        assertTrue(orderRepository.existsById(cookedOrderId));
        assertEquals(orderRepository.findById(cookedOrderId).get().getStatus(), Status.Delivered);
        int orderedOrderId = 1;
        ResponseEntity<String> orderedOrder = restTemplate.postForEntity(getUrl() + "completeOrder?t=" + token,
                orderedOrderId,
                String.class);
        assertEquals(orderedOrder.getStatusCode(), HttpStatus.BAD_REQUEST);
        int inProgressOrderId = 2;
        ResponseEntity<String> inProgressOrder = restTemplate.postForEntity(getUrl() + "completeOrder?t=" + token,
                inProgressOrderId,
                String.class);
        assertEquals(inProgressOrder.getStatusCode(), HttpStatus.BAD_REQUEST);
        int deliveredOrderId = 4;
        ResponseEntity<String> deliveredOrder = restTemplate.postForEntity(getUrl() + "completeOrder?t=" + token,
                deliveredOrderId,
                String.class);
        assertEquals(deliveredOrder.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
