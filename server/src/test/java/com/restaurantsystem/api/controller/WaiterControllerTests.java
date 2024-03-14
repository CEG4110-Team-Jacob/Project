package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.service.AuthenticationServiceImpl;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
public class WaiterControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private String token;

    @BeforeEach
    void login() {
        token = authenticationService.login("jd", "janedoe").get();
    }

    @Test
    void contextLoads() {
        assertNotNull(restTemplate);
        assertNotNull(authenticationService);
    }

    record ListOfOrders(List<Order> orders) {
    }

    record Order(int id, List<Item> items, Date timeOrdered, Status status, int totalPrice) {
    }

    record Item(String description, int id, String name, ItemType type, int price, boolean inStock) {
    };

    @Transactional
    @Test
    void getOrder() {
        String query = "t=" + token;
        ResponseEntity<ListOfOrders> response = restTemplate
                .getForEntity("http://localhost:" + port + "/waiter/order?" + query, ListOfOrders.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }
}
