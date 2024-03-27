package com.restaurantsystem.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.restaurantsystem.api.DatabasePopulate.Login;
import com.restaurantsystem.api.service.AuthenticationService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerParentTests {
    @LocalServerPort
    protected int port;

    @Autowired
    protected AuthenticationService authenticationService;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String token;

    protected String path = "";

    protected Login login;

    protected String getUrl() {
        return "http://localhost:" + port + "/" + path + "/";
    }

    @BeforeEach
    void login() {
        token = authenticationService.login(login.username(), login.password()).get();
    }

}
