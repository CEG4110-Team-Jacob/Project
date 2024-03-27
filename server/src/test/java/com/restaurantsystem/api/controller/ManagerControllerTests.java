package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;
import com.restaurantsystem.api.shared.manager.ManagerViewWorker.ListWorkers;

public class ManagerControllerTests extends ControllerParentTests {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    ItemRepository itemRepository;

    public ManagerControllerTests() {
        this.login = DatabasePopulate.Manager1;
        this.path = "/manager";
    }

    @Test
    void getItems() throws Exception {
        var items = getMockMvcResultType("/items", ListOfItems.class);
        assertTrue(items.items().size() > 0);
    }

    @Test
    void createAccount() throws Exception {
        var data = toJson(new PostCreateAccount("baba", "Not", 30, Job.Host, "hsdagai", "asdfhuas", new HashSet<>()));
        postMockMvcResult("/createWorker", data);
        assertTrue(workerRepository.existsByUsername("hsdagai"));
    }

    @Test
    void addItem() throws Exception {
        var data = toJson(new AddItem("asda", "gasd", 200, true, ItemType.Food));
        postMockMvcResult("/addItem", data);
        assertTrue(itemRepository.existsByName("asda"));
    }

    @Test
    void getWorkers() throws Exception {
        var workers = getMockMvcResultType("/workers", ListWorkers.class);
        assertTrue(workers.workers().size() > 2);
    }
}
