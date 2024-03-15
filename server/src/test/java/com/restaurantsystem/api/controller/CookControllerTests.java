package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.shared.TestSharedItem;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
public class CookControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;

    private String getUrl() {
        return "http://localhost:" + port + "/cook/";
    }

    @BeforeEach
    void login() {
        token = authenticationService.login(DatabasePopulate.Cook1.username(), DatabasePopulate.Cook1.password()).get();
    }

    record Orders(List<Order> orders) {
        record Order(List<TestSharedItem> items, Date timeOrdered, Status status, int id) {
        }
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

}
