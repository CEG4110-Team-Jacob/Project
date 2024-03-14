package com.restaurantsystem.api;

import static org.mockito.ArgumentMatchers.same;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;

public class DatabasePopulate implements BeforeAllCallback {

    ItemRepository itemRepository;

    OrderRepository orderRepository;
    WorkerRepository workerRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static boolean first = true;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!first)
            return;
        first = false;
        ApplicationContext context2 = SpringExtension.getApplicationContext(context);
        itemRepository = context2.getBean(ItemRepository.class);
        orderRepository = context2.getBean(OrderRepository.class);
        workerRepository = context2.getBean(WorkerRepository.class);
        populateWorkers();
        populateItems();
        populateOrders();
    }

    public void populateItems() {
        Item burger = new Item();
        burger.setInStock(true);
        burger.setName("Hamburger");
        burger.setDescription(
                "A round patty of ground beef, fried or grilled and typically served on a bun or roll and garnished with various condiments.");
        burger.setPrice(1000);
        burger.setType(ItemType.Food);
        itemRepository.save(burger);

        Item water = new Item();
        water.setInStock(true);
        water.setName("Water");
        water.setDescription("Literally just extremely overpriced water.");
        water.setPrice(10000);
        water.setType(ItemType.Beverage);
        itemRepository.save(water);

        Item fries = new Item();
        fries.setInStock(false);
        fries.setName("Fries");
        fries.setDescription(
                "Batonnet or allumette-cut deep-fried potatoes of disputed origin from Belgium or France. They are prepared by cutting potatoes into even strips, drying them, and frying them, usually in a deep fryer.");
        fries.setPrice(300);
        fries.setType(ItemType.Food);
        itemRepository.save(fries);
    }

    public void populateWorkers() {
        Worker waiter1 = new Worker();
        waiter1.setAge(18);
        waiter1.setFirstName("Jane");
        waiter1.setLastName("Doe");
        waiter1.setJob(Job.Waiter);
        waiter1.setUsername("jd");
        waiter1.setPasswordHash(passwordEncoder.encode("janedoe"));
        waiter1.setTables(null);
        workerRepository.save(waiter1);

        Worker host = new Worker();
        host.setAge(16);
        host.setFirstName("Jim");
        host.setLastName("Indigo");
        host.setJob(Job.Host);
        host.setUsername("jin");
        host.setPasswordHash(passwordEncoder.encode("jindigo"));
        host.setTables(null);
        workerRepository.save(host);
    }

    public void populateOrders() {
        Order o1 = new Order();
        o1.setItems(new ArrayList<>());
        itemRepository.findAllById(Arrays.asList(1, 2, 3)).forEach(o1.getItems()::add);
        o1.setStatus(Status.Ordered);
        o1.setTimeOrdered(new Date());
        o1.setTimeCompleted(null);
        o1.setTotalPrice();
        o1.setWaiter(workerRepository.findById(1).get());
        orderRepository.save(o1);
    }

}
