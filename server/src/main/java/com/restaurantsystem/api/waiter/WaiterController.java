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
import com.restaurantsystem.api.data.enums.Job;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.service.interfaces.DataConversionService;
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
    AuthenticationService authenticationService;
    @Autowired
    DataConversionService dataConversion;

    // TODO Connect to database
    @GetMapping("/order")
    public ResponseEntity<GetOrderWaiter> getOrder(@RequestParam(value = "t") String token,
            @RequestParam(value = "id") int orderId) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (worker.get().getJob() != Job.Waiter)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Optional<Order> order = orderRepository.findById(orderId);
        Order order1 = new Order();
        Optional<Order> order = Optional.of(order1);
        if (order.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<GetOrderWaiter>(dataConversion.toSharedOrder(order.get()), HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    public HttpStatus addOrder(@RequestBody PostOrderWaiter order, @RequestParam(value = "t") String token) {
        Optional<Worker> worker = authenticationService.authenticate(token);
        if (worker.isEmpty())
            return HttpStatus.UNAUTHORIZED;
        if (worker.get().getJob() != Job.Waiter)
            return HttpStatus.UNAUTHORIZED;
        Order o = dataConversion.toOrder(order, worker.get().getId());
        orderRepository.save(o);

        return HttpStatus.OK;
    }

}
