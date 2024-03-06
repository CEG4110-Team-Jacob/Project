package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.restaurantsystem.api.repos.WorkerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthenticationServiceTest {
    @Mock
    WorkerRepository workerRepository;

    @InjectMocks
    AuthenticationService authenticationService;

    @Test
    void login() {
        assertNull(authenticationService.login("LOLNOPE", ""));
        // FIXME login is returning null
        assertEquals(authenticationService.login("test", ""), "hufhiohwef");
        // assertEquals(authenticationService.login("test", ""), "hufhiohwef");
    }
}
