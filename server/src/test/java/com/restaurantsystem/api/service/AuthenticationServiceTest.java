package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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

    @Test
    void createAccount() {
        Optional<Worker> worker1 = authenticationService
                .addWorker(new PostCreateAccount("a", "b", 20, Job.Waiter, "bill", "tom", new HashSet<>()));
        assertTrue(worker1.isPresent());
        assertNotNull(worker1.get());
        assertTrue(workerRepository.existsById(worker1.get().getId()));
        assertEquals(workerRepository.findById(worker1.get().getId()).get().getUsername(), "bill");
        Optional<Worker> underaged = authenticationService
                .addWorker(new PostCreateAccount("babab", "is", 10, Job.Manager, "you", "LOL", new HashSet<>()));
        assertTrue(underaged.isEmpty());
        Optional<Worker> sameUserName = authenticationService
                .addWorker(new PostCreateAccount("hdsjaf", "jabs", 20, Job.Host, DatabasePopulate.Waiter1.username(),
                        "asd", new HashSet<>()));
        assertTrue(sameUserName.isEmpty());
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
        Optional<String> managerToken = authenticationService.login(DatabasePopulate.Manager1.username(),
                DatabasePopulate.Manager1.password());
        assertTrue(managerToken.isPresent());
        assertTrue(authenticationService.hasJobAndAuthenticate(managerToken.get(), Job.Waiter).isPresent());
    }
}
