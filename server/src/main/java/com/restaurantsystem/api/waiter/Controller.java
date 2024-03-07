package com.restaurantsystem.api.waiter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.service.interfaces.DataConversionService;
import com.restaurantsystem.api.shared.waiter.OrderWaiter;

@RestController
@RequestMapping(path = "/waiter")
public class Controller {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    DataConversionService dataConversion;

    // TODO
    @GetMapping("/order")
    public ResponseEntity<OrderWaiter> getOrder(@RequestParam(value = "t") String token,
            @RequestParam(value = "id") int orderId) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Optional<Order> order = orderRepository.findById(orderId);
        Optional<Order> order = Optional.of(new Order());
        if (order.isEmpty())
            // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            order = Optional.of(new Order());
        return new ResponseEntity<OrderWaiter>(dataConversion.order(order.get()), HttpStatus.OK);
    }
}
