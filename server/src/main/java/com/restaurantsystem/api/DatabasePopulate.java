package com.restaurantsystem.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;

/**
 * Populates the Database for testing
 */
@Service
public class DatabasePopulate {

    public record Login(String username, String password) {
    }

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static final Login Waiter1 = new Login("jd", "janedoe");
    public static final Login Host1 = new Login("jin", "jindigo");
    public static final Login Cook1 = new Login("TimCook", "Apple");
    public static final Login Manager1 = new Login("OrangeGuy", "Wall");

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    TableRepository tableRepository;

    public void populate() {
        populateWorkers();
        populateTables();
        populateItems();
        populateOrders();
    }

    public void populateTables() {
        Table table1 = new Table();
        table1.setNumSeats(3);
        table1.setOccupied(false);
        tableRepository.save(table1);
    }

    public void populateItems() {
        Item burger = new Item();
        burger.setInStock(true);
        burger.setName("Hamburger");
        burger.setDescription(
                "A round patty of ground beef, fried or grilled and typically served on a bun or roll and garnished with various condiments.");
        burger.setPrice(1000);
        burger.setType(ItemType.Food);
        burger.setId(1);
        itemRepository.save(burger);

        Item water = new Item();
        water.setInStock(true);
        water.setName("Water");
        water.setDescription("Literally just extremely overpriced water.");
        water.setPrice(10000);
        water.setType(ItemType.Beverage);
        water.setId(2);
        itemRepository.save(water);

        Item fries = new Item();
        fries.setInStock(false);
        fries.setName("Fries");
        fries.setDescription(
                "Batonnet or allumette-cut deep-fried potatoes of disputed origin from Belgium or France. They are prepared by cutting potatoes into even strips, drying them, and frying them, usually in a deep fryer.");
        fries.setPrice(300);
        fries.setType(ItemType.Food);
        fries.setId(3);
        itemRepository.save(fries);
    }

    public void populateWorkers() {
        Worker waiter1 = new Worker();
        waiter1.setAge(18);
        waiter1.setFirstName("Jane");
        waiter1.setLastName("Doe");
        waiter1.setJob(Job.Waiter);
        waiter1.setUsername(Waiter1.username);
        waiter1.setPasswordHash(passwordEncoder.encode(Waiter1.password));
        waiter1.setTables(null);
        workerRepository.save(waiter1);

        Worker host = new Worker();
        host.setAge(16);
        host.setFirstName("Jim");
        host.setLastName("Indigo");
        host.setJob(Job.Host);
        host.setUsername(Host1.username);
        host.setPasswordHash(passwordEncoder.encode(Host1.password));
        host.setTables(null);
        workerRepository.save(host);

        Worker cook = new Worker();
        cook.setAge(63);
        cook.setFirstName("Tim");
        cook.setLastName("Cook");
        cook.setJob(Job.Cook);
        cook.setUsername(Cook1.username);
        cook.setPasswordHash(passwordEncoder.encode(Cook1.password));
        cook.setTables(null);
        workerRepository.save(cook);

        Worker manager = new Worker();
        manager.setAge(77);
        manager.setFirstName("Donald");
        manager.setLastName("NotTrump");
        manager.setJob(Job.Manager);
        manager.setUsername(Manager1.username);
        manager.setPasswordHash(passwordEncoder.encode(Manager1.password));
        manager.setTables(null);
        workerRepository.save(manager);
    }

    public void populateOrders() {
        Order o1 = new Order();
        o1.setItems(new ArrayList<>());
        itemRepository.findAllById(Arrays.asList(1, 2, 3)).forEach(o1.getItems()::add);
        o1.setStatus(Status.Ordered);
        o1.setTimeOrdered(new Date());
        o1.setTimeCompleted(null);
        o1.setTotalPrice();
        o1.setWaiter(workerRepository.findByUsername(Waiter1.username).get());
        orderRepository.save(o1);

        Order o2 = new Order();
        o2.setItems(new ArrayList<>());
        itemRepository.findAllById(Arrays.asList(2, 3)).forEach(o2.getItems()::add);
        o2.setStatus(Status.InProgress);
        o2.setTimeOrdered(new Date());
        o2.setTimeCompleted(null);
        o2.setTotalPrice();
        o2.setWaiter(workerRepository.findByUsername(Waiter1.username).get());
        orderRepository.save(o2);

        Order o3 = new Order();
        o3.setItems(new ArrayList<>());
        itemRepository.findAllById(Arrays.asList(1, 3)).forEach(o3.getItems()::add);
        o3.setStatus(Status.Cooked);
        o3.setTimeOrdered(new Date());
        o3.setTimeCompleted(null);
        o3.setTotalPrice();
        o3.setWaiter(workerRepository.findByUsername(Waiter1.username).get());
        orderRepository.save(o3);

        Order o4 = new Order();
        o4.setItems(new ArrayList<>());
        itemRepository.findAllById(Arrays.asList(1)).forEach(o4.getItems()::add);
        o4.setStatus(Status.Delivered);
        o4.setTimeOrdered(new Date());
        o4.setTimeCompleted(null);
        o4.setTotalPrice();
        o4.setWaiter(workerRepository.findByUsername(Waiter1.username).get());
        orderRepository.save(o4);
    }

}
