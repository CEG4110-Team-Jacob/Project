package com.restaurantsystem.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.cooks.GetOrderCook;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * A Controller for cooks
 */
@RestController
@RequestMapping(path = "/cook")
public class CookController {
    /**
     * A list of orders that Cooks get
     */
    public record Orders(List<GetOrderCook> orders) {
    }

    /**
     * PostSetStatus
     */
    public record PostSetStatus(Status status, int orderId) {
    }

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * Gets all the orders with status InProgress or Ordered
     * 
     * @param t token
     * @return orders
     */
    @GetMapping("/getOrders")
    public ResponseEntity<Orders> getOrders(@RequestParam String t) {
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<GetOrderCook> orders = orderRepository.findAllByStatusIn(
                Arrays.asList(Status.InProgress, Status.Ordered, Status.Cooked),
                GetOrderCook.class);
        return new ResponseEntity<>(new Orders(orders), HttpStatus.OK);
    }

    @PostMapping("/setStatus")
    @Transactional
    public ResponseEntity<Boolean> setOrderStatus(@RequestParam String t, @RequestBody PostSetStatus body) {
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(body.orderId());
        if (order.isEmpty() || !Status.cook(body.status()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        order.get().setStatus(body.status());
        order = Optional.of(orderRepository.save(order.get()));
        // if (Status.Cooked == body.status()) {
        // messagingTemplate.convertAndSend("/topic/order/" +
        // order.get().getWaiter().getId());
        // }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Sets an item as depleted or not in stock
     * 
     * @param t  token
     * @param id item id
     */
    @PostMapping("/itemDepleted")
    @Transactional
    public ResponseEntity<Boolean> itemDepleted(@RequestParam String t, @RequestBody int id) {
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty() || !item.get().isInStock())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        item.get().setInStock(false);
        itemRepository.save(item.get());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Sets an item as in stock
     * 
     * @param t  token
     * @param id item id
     */
    @PostMapping("/itemRestocked")
    @Transactional
    public ResponseEntity<Boolean> itemRestocked(@RequestParam String t, @RequestBody int id) {
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty() || item.get().isInStock())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        item.get().setInStock(true);
        itemRepository.save(item.get());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
