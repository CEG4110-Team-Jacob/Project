package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.WorkerRepository;

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

    @Test
    void logout() {
        Optional<String> t = authenticationService.login(DatabasePopulate.Waiter1.username(),
                DatabasePopulate.Waiter1.password());
        assertTrue(t.isPresent());
        assertTrue(authenticationService.authenticate(t.get()).isPresent());
        authenticationService.logout(t.get());
        assertFalse(authenticationService.authenticate(t.get()).isPresent());
    }

    /**
     * Tests the login features as well as authentication
     */
    @Test
    void login() {
        assertTrue(authenticationService.login("jlgebg", "uiguef").isEmpty());
        Optional<String> t = authenticationService.login(DatabasePopulate.Waiter1.username(),
                DatabasePopulate.Waiter1.password());
        assertTrue(t.isPresent());
        authenticate(t.get());
        hasJobAndAuthenticate(t.get());
    }

    /**
     * Tests authentication
     * 
     * @param token Valid token
     */
    void authenticate(String token) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        assertTrue(worker.isPresent());
        assertTrue(worker.get().getUsername().equals(DatabasePopulate.Waiter1.username()));
        assertTrue(passwordEncoder.matches(DatabasePopulate.Waiter1.password(), worker.get().getPasswordHash()));
        assertTrue(authenticationService.authenticate("hqe8fh").isEmpty());
    }

    void hasJobAndAuthenticate(String token) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        assertTrue(worker.isPresent());
        assertTrue(authenticationService.hasJobAndAuthenticate(token, Job.Host).isEmpty());
    }
}
