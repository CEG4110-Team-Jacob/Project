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
import com.restaurantsystem.api.shared.all.ListOfItems;
import com.restaurantsystem.api.shared.all.SharedItem;
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
    /**
     * Record that waiters get for all the orders
     */
    public record Orders(List<GetOrderWaiter> orders) {
    }

    /**
     * TableList
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
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<GetOrderWaiter> order = orderRepository.findAllByStatusInAndWaiter(
                Arrays.asList(Status.InProgress, Status.Ordered, Status.Cooked), worker.get(),
                GetOrderWaiter.class);
        ResponseEntity<Orders> orders = new ResponseEntity<Orders>(new Orders(order),
                HttpStatus.OK);
        return orders;
    }

    /**
     * Gets all the items on the menu
     * 
     * @param t token
     * @return Items
     */
    @GetMapping("/items")
    public ResponseEntity<ListOfItems> getItems(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<SharedItem> items = itemRepository.findAllBy(SharedItem.class);
        return new ResponseEntity<ListOfItems>(new ListOfItems(items), HttpStatus.OK);
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
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Order o = new Order();
        List<Item> items = new ArrayList<>();
        itemRepository.findAllById(order.items()).forEach(items::add);
        o.setItems(items);
        o.setTimeOrdered(new Date());
        o.setTimeCompleted(null);
        o.setTotalPrice();
        o.setStatus(Status.Ordered);
        o.setWaiter(worker.get());
        Optional<Table> table = tableRepository.findById(order.tableId());
        if (table.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        o.setTable(table.get());
        o = orderRepository.save(o);
        table.get().setOccupied(true);
        tableRepository.save(table.get());
        return new ResponseEntity<>(o.getId(), HttpStatus.OK);
    }

    /**
     * Marks an order as complete
     * 
     * @param orderId
     * @param token
     */
    @PostMapping("/completeOrder")
    @Transactional
    public ResponseEntity<Boolean> completeOrder(@RequestBody Integer orderId,
            @RequestParam(value = "t") String token) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty() || order.get().getStatus() != Status.Cooked)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        order.get().setStatus(Status.Delivered);
        order = Optional.of(orderRepository.save(order.get()));
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
     */
    @PostMapping("/cancelOrder")
    @Transactional
    public ResponseEntity<Boolean> cancelOrder(@RequestBody Integer orderId, @RequestParam(value = "t") String token) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(orderId);
        order.get().setStatus(Status.Canceled);
        order = Optional.of(orderRepository.save(order.get()));
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
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<TableProjection> tables = tableRepository.findAllByIsActive(true, TableProjection.class);
        return new ResponseEntity<>(new TableList(tables), HttpStatus.OK);
    }

}
