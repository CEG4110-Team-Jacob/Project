package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthenticationServiceTest {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AuthenticationService authenticationService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeTransaction
    void populate() {
        Worker worker1 = new Worker();
        worker1.setUsername("test");
        worker1.setPasswordHash(passwordEncoder.encode("test"));
        workerRepository.save(worker1);
    }

    @Test
    @Transactional
    void login() {
        assertTrue(authenticationService.login("jlgebg", "uiguef").isEmpty());
        Optional<String> t = authenticationService.login("test", "test");
        assertTrue(t.isPresent());
        authenticate(t.get());
    }

    void authenticate(String token) {
        assertTrue(authenticationService.authenticate(token).isPresent());
        assertTrue(authenticationService.authenticate("hqe8fh").isEmpty());
    }
}
