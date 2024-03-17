package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;

public class ManagerControllerTests extends ControllerParentTests {
    @Autowired
    WorkerRepository workerRepository;

    public ManagerControllerTests() {
        this.login = DatabasePopulate.Manager1;
        this.path = "manager";
    }

    @Test
    void getItems() {
        ResponseEntity<ListOfItems> items = restTemplate.getForEntity(getUrl() + "items?t=" + token,
                ListOfItems.class);
        assertEquals(items.getStatusCode(), HttpStatus.OK);
        assertTrue(items.getBody().items().size() > 0);
    }

    @Test
    void createAccount() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() + "createWorker?t=" + token,
                new PostCreateAccount("baba", "Not", 30, Job.Host, "hsdagai", "asdfhuas"), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(workerRepository.existsByUsername("hsdagai"));
    }
}
