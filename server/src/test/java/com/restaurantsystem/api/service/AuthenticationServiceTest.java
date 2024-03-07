package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthenticationServiceTest {
    // @Mock
    // WorkerRepository workerRepository;

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Test
    void login() {
        assertNull(authenticationService.login("LOLNOPE", ""));
        // FIXME login is returning null
        assertEquals(authenticationService.login("test", ""), "hufhiohwef");
        // assertEquals(authenticationService.login("test", ""), "hufhiohwef");
    }
}
