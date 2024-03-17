package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.TestSharedItem;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
public class CookControllerTests {
    record Orders(List<Order> orders) {
        record Order(List<TestSharedItem> items, Date timeOrdered, Status status, int id) {
        }
    }

    @LocalServerPort
    private int port;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private String token;

    @BeforeEach
    void login() {
        token = authenticationService.login(DatabasePopulate.Cook1.username(), DatabasePopulate.Cook1.password()).get();
    }

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
        ResponseEntity<String> fail = restTemplate.postForEntity(getUrl() +
                "cookingOrder?t=" + token, 2,
                String.class);
        assertEquals(fail.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    // @Test
    // void completeOrder() {
    // ResponseEntity<String> response = restTemplate.postForEntity(getUrl() +
    // "completeOrder?t=" + token, 2,
    // String.class);
    // assertEquals(response.getStatusCode(), HttpStatus.OK);
    // Optional<Order> order = orderRepository.findById(2);
    // assertTrue(order.isPresent());
    // assertEquals(order.get().getStatus(), Status.Cooked);
    // ResponseEntity<String> fail = restTemplate.postForEntity(getUrl() +
    // "cookingOrder?t=" + token, 1,
    // String.class);
    // assertEquals(fail.getStatusCode(), HttpStatus.BAD_REQUEST);
    // }

    @Test
    void getItems() {
        ResponseEntity<ListOfItems> items = restTemplate.getForEntity(getUrl() + "items?t=" + token,
                ListOfItems.class);
        assertEquals(items.getStatusCode(), HttpStatus.OK);
        assertTrue(items.getBody().items().size() > 0);
    }

    private String getUrl() {
        return "http://localhost:" + port + "/cook/";
    }
}
