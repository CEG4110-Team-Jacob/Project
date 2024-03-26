package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.ManagerViewWorker;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;

public class ManagerControllerTests extends ControllerParentTests {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    ItemRepository itemRepository;

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
                new PostCreateAccount("baba", "Not", 30, Job.Host, "hsdagai", "asdfhuas", new HashSet<>()),
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(workerRepository.existsByUsername("hsdagai"));
    }

    @Test
    void addItem() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() + "addItem?t=" + token,
                new AddItem("asda", "gasd", 200, true, ItemType.Food), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(itemRepository.existsByName("asda"));
    }

    @Test
    void getWorkers() {
        ResponseEntity<ManagerViewWorker.ListWorkers> response = restTemplate
                .getForEntity(getUrl() + "workers?t=" + token, ManagerViewWorker.ListWorkers.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().workers().size() > 2);
    }
}
