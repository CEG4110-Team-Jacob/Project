package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;
import com.restaurantsystem.api.shared.manager.PostTables;
import com.restaurantsystem.api.shared.manager.PostTables.PostTable;
import com.restaurantsystem.api.shared.manager.ManagerViewWorker.ListWorkers;

@Transactional
@Rollback(true)
public class ManagerControllerTests extends ControllerParentTests {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TableRepository tableRepository;

    public ManagerControllerTests() {
        this.login = DatabasePopulate.Manager1;
        this.path = "/manager";
    }

    @Test
    void createAccount() throws Exception {
        var data = toJson(new PostCreateAccount("baba", "Not", 30, Job.Host, "hsdagai", "asdfhuas"));
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

    @Test
    void deleteWorker() throws Exception {
        postMockMvcResult("/deleteWorker", "1");
        assertFalse(workerRepository.findById(1).get().isActive());
    }

    /**
     * PostChangeItem
     */
    public record PostChangeItem(int id, AddItem details) {
    }

    @Test
    void changeItem() throws Exception {
        var data = toJson(new PostChangeItem(1, new AddItem("guhd", "inasgf", 37825, false, ItemType.Food)));
        postMockMvcResult("/changeItem", data);
        var newItem = itemRepository.findById(1).get();
        assertEquals(newItem.getName(), "guhd");
        assertEquals(newItem.getDescription(), "inasgf");
        assertEquals(newItem.getPrice(), 37825);
        assertFalse(newItem.isInStock());
        assertEquals(newItem.getType(), ItemType.Food);
    }

    @Test
    void deleteItem() throws Exception {
        postMockMvcResult("/deleteItem", "1");
        assertFalse(itemRepository.findById(1).get().isActive());
    }

    @Test
    void setTables() throws Exception {
        var table = new PostTable(10, 10, 0, 10, 5);
        var json = toJson(new PostTables(new HashSet<>(Arrays.asList(table))));
        postMockMvcResult("/setTables", json);
        assertTrue(tableRepository.findAllByIsActive(true, Table.class).size() == 1);
    }
}
