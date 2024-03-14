package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.restaurantsystem.api.controllers.WaiterController;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationServiceImpl;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;

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

    @Autowired
    private WorkerRepository workerRepository;

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

    record ListOfOrders(List<GetOrderWaiter> orders) {
    }

    @Transactional
    @Test
    void getOrder() {
        String query = "t=" + token;
        ResponseEntity<WaiterController.Orders> response = restTemplate
                .getForEntity("http://localhost:" + port + "/waiter/order?" + query, WaiterController.Orders.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
