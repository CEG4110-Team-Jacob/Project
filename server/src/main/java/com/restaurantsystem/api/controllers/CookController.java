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
import com.restaurantsystem.api.shared.all.ListOfItems;
import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.cooks.GetOrderCook;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WorkerRepository workerRepository;

    /**
     * Gets all items' details
     * 
     * @param t token
     * @return items
     */
    @GetMapping("/items")
    public ResponseEntity<ListOfItems> getItems(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<SharedItem> items = itemRepository.findAllBy(SharedItem.class);
        return new ResponseEntity<ListOfItems>(new ListOfItems(items), HttpStatus.OK);
    }

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
        List<GetOrderCook> orders = orderRepository.findAllByStatusIn(Arrays.asList(Status.InProgress, Status.Ordered),
                GetOrderCook.class);
        return new ResponseEntity<>(new Orders(orders), HttpStatus.OK);
    }

    /**
     * Sets an order as InProgress or Cooking
     * 
     * @param t  token
     * @param id order id
     */
    @PostMapping("/cookingOrder")
    @Transactional
    public ResponseEntity<Boolean> cookingOrder(@RequestParam String t,
            @RequestBody int id) {
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty() || order.get().getStatus() != Status.Ordered)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        order.get().setStatus(Status.InProgress);
        orderRepository.save(order.get());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Autowired
    WaiterController waiterController;

    /**
     * Sets an order as cooked
     * 
     * @param t  token
     * @param id order id
     */
    @PostMapping("/completeOrder")
    @Transactional
    public ResponseEntity<Boolean> completeOrder(@RequestParam String t,
            @RequestBody int id) {
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty() || order.get().getStatus() != Status.InProgress)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        order.get().setStatus(Status.Cooked);
        orderRepository.save(order.get());
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
