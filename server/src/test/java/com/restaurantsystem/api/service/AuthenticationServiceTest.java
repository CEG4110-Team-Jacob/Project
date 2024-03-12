package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.restaurantsystem.api.service.interfaces.AuthenticationService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthenticationServiceTest {
    @Autowired
    AuthenticationService authenticationService;

    @Test
    void login() {
        assertTrue(authenticationService.login("LOLNOPE", "").isEmpty());
        assertEquals(authenticationService.login("test", "").get(), "hufhiohwef");
    }
}
