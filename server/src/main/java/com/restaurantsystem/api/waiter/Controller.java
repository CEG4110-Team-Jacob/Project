package com.restaurantsystem.api.waiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.WorkerRepository;

@RestController
@RequestMapping(path = "/waiter")
public class Controller {
    @Autowired
    WorkerRepository userRepository;

    // TODO
    @GetMapping("/order")
    public ResponseEntity<Order> getOrder(@RequestParam(value = "t") String token,
            @RequestParam(value = "id") int orderId) {
        Worker worker = userRepository.authenticate(token);
        if (worker == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Order test = new Order();
        test.setId(10);
        return new ResponseEntity<Order>(test, HttpStatus.OK);
    }
}
