package com.restaurantsystem.api.controllers;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.all.ListOfItems;
import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/waiter")
public class WaiterController {
    public record Orders(List<GetOrderWaiter> orders) {
    }

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AuthenticationService authenticationService;

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

    @GetMapping("/items")
    public ResponseEntity<ListOfItems> getItems(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<SharedItem> items = itemRepository.findAllBy(SharedItem.class);
        return new ResponseEntity<ListOfItems>(new ListOfItems(items), HttpStatus.OK);
    }

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
        o = orderRepository.save(o);
        return new ResponseEntity<>(o.getId(), HttpStatus.OK);
    }

    @PostMapping("/completeOrder")
    @Transactional
    public ResponseEntity<String> completeOrder(@RequestBody Integer orderId, @RequestParam(value = "t") String token) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty() || order.get().getStatus() != Status.Cooked)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        order.get().setStatus(Status.Delivered);
        orderRepository.save(order.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cancelOrder")
    @Transactional
    public ResponseEntity<String> cancelOrder(@RequestBody Integer orderId, @RequestParam(value = "t") String token) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(token, Job.Waiter);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Order> order = orderRepository.findById(orderId);
        order.get().setStatus(Status.Canceled);
        orderRepository.save(order.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SendTo("/{waiter_id}")
    public int orderCompleted(@PathVariable int waiter_id, int order_id) {
        return order_id;
    }

}
