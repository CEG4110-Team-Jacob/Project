package com.restaurantsystem.api.controllers;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;
import com.restaurantsystem.api.shared.waiter.TableProjection;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for Waiters
 */
@RestController
@RequestMapping(path = "/waiter")
public class WaiterController {
    // The statuses of current orders
    private static final List<Status> TABLE_OCCUPIED = Arrays.asList(Status.InProgress, Status.Ordered, Status.Cooked,
            Status.Delivered);

    /**
     * All the orders the waiter has placed and are not completed
     */
    public record Orders(List<GetOrderWaiter> orders) {
    }

    /**
     * Table List
     */
    public record TableList(List<TableProjection> tables) {
    }

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TableRepository tableRepository;

    @Autowired
    AuthenticationService authenticationService;

    /**
     * Gets all the current orders the waiter made
     * 
     * @param token token
     * @return Orders
     */
    @GetMapping("/order")
    public ResponseEntity<Orders> getOrder(@RequestParam(value = "t") String token) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the orders
        List<GetOrderWaiter> order = orderRepository.findAllByStatusInAndWaiter(
                TABLE_OCCUPIED, worker.get(),
                GetOrderWaiter.class);
        return new ResponseEntity<Orders>(new Orders(order),
                HttpStatus.OK);
    }

    /**
     * Creates an order
     * 
     * @param order order Details
     * @param token
     * @return id of the order
     */
    @PostMapping("/addOrder")
    @Transactional
    public ResponseEntity<Integer> addOrder(@RequestBody PostOrderWaiter order,
            @RequestParam(value = "t") String token) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Create the order
        Order o = new Order();
        List<Item> items = new ArrayList<>();
        order.items().stream().forEach(item -> items.add(new Item(item)));
        o.setItems(items);
        o.setTimeOrdered(new Date());
        o.setTimeCompleted(null);
        o.setTotalPrice();
        o.setStatus(Status.Ordered);
        o.setWaiter(worker.get());
        // Get the table
        Optional<Table> table = tableRepository.findById(order.tableId());
        // Check the table's occupation
        if (table.isEmpty() || table.get().isOccupied())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // Update the table and add the order
        o.setTable(table.get());
        o = orderRepository.save(o);
        table.get().setOccupied(true);
        tableRepository.save(table.get());
        return new ResponseEntity<>(o.getId(), HttpStatus.OK);
    }

    /**
     * Marks an order as Delivered
     * 
     * @param orderId
     * @param token
     * @return Response
     */
    @PostMapping("/completeOrder")
    @Transactional
    public ResponseEntity<Boolean> completeOrder(@RequestBody Integer orderId,
            @RequestParam(value = "t") String token) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the order
        Optional<Order> order = orderRepository.findById(orderId);
        // Check the status of the order
        if (order.isEmpty() || order.get().getStatus() != Status.Cooked)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // Update the order
        order.get().setStatus(Status.Delivered);
        orderRepository.save(order.get());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Mark an order as completed
     * 
     * @param orderId
     * @param token
     * @return Response
     */
    @PostMapping("/orderDone")
    @Transactional
    public ResponseEntity<Boolean> orderDone(@RequestBody Integer orderId,
            @RequestParam(value = "t") String token) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the order
        Optional<Order> order = orderRepository.findById(orderId);
        // Check the status of the order
        if (order.isEmpty() || order.get().getStatus() != Status.Delivered)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // Update the order
        order.get().setStatus(Status.Completed);
        order = Optional.of(orderRepository.save(order.get()));
        // Update the table
        Table table = order.get().getTable();
        table.setOccupied(false);
        tableRepository.save(table);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Marks an order as cancelled
     * 
     * @param orderId
     * @param token
     * @return Response
     */
    @PostMapping("/cancelOrder")
    @Transactional
    public ResponseEntity<Boolean> cancelOrder(@RequestBody Integer orderId, @RequestParam(value = "t") String token) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the order
        Optional<Order> order = orderRepository.findById(orderId);
        // Update the order
        order.get().setStatus(Status.Canceled);
        order = Optional.of(orderRepository.save(order.get()));
        // Update the table
        Table table = order.get().getTable();
        table.setOccupied(false);
        tableRepository.save(table);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Gets the tables
     * 
     * @param t token
     * @return list of tables
     */
    @GetMapping("/tables")
    public ResponseEntity<TableList> getTables(@RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Gets all the tables
        List<TableProjection> tables = tableRepository.findAllByIsActive(true, TableProjection.class);
        return new ResponseEntity<>(new TableList(tables), HttpStatus.OK);
    }

}
