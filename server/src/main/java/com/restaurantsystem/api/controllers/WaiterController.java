package com.restaurantsystem.api.controllers;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.shared.waiter.GetOrderWaiter;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/waiter")
public class WaiterController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/order")
    public ResponseEntity<GetOrderWaiter> getOrder(@RequestParam(value = "t") String token,
            @RequestParam(value = "id") int orderId) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (worker.get().getJob() != Job.Waiter)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<GetOrderWaiter> order = orderRepository.findById(orderId, GetOrderWaiter.class);
        if (order.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<GetOrderWaiter>(order.get(),
                HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    public HttpStatus addOrder(@RequestBody PostOrderWaiter order,
            @RequestParam(value = "t") String token) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        if (worker.isEmpty())
            return HttpStatus.UNAUTHORIZED;
        if (worker.get().getJob() != Job.Waiter)
            return HttpStatus.UNAUTHORIZED;
        Order o = new Order();
        List<Item> items = new ArrayList<>();
        itemRepository.findAllById(order.items()).forEach(items::add);
        o.setItems(items);
        o.setTimeOrdered(new Date());
        o.setTimeCompleted(null);
        o.setTotalPrice();
        o.setStatus(Status.Ordered);
        o.setWaiter(worker.get());
        orderRepository.save(o);
        return HttpStatus.OK;
    }

}
