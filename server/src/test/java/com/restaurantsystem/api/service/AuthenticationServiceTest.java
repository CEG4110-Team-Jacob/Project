package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
public class AuthenticationServiceTest {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AuthenticationService authenticationService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void contextLoads() {
        assertNotNull(workerRepository);
        assertNotNull(authenticationService);
        assertNotNull(passwordEncoder);
    }

    /**
     * Tests the login features as well as authentication
     */
    @Test
    @Transactional
    void login() {
        assertTrue(authenticationService.login("jlgebg", "uiguef").isEmpty());
        Optional<String> t = authenticationService.login("jd", "janedoe");
        assertTrue(t.isPresent());
        authenticate(t.get());
    }

    /**
     * Tests authentication
     * 
     * @param token Valid token
     */
    void authenticate(String token) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        assertTrue(worker.isPresent());
        assertTrue(worker.get().getUsername().equals("jd"));
        assertTrue(passwordEncoder.matches("janedoe", worker.get().getPasswordHash()));
        assertTrue(authenticationService.authenticate("hqe8fh").isEmpty());
    }
}
