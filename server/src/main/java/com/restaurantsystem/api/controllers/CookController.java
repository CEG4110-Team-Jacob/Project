package com.restaurantsystem.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.shared.cooks.GetOrderCook;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/cook")
public class CookController {
    public record Orders(List<GetOrderCook> orders) {
    }

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/getOrders")
    public ResponseEntity<Orders> getOrders(@RequestParam String t) {
        System.out.println(workerRepository.findByUsername("TimCook").get().getToken());
        Optional<Worker> cook = authenticationService.hasJobAndAuthenticate(t, Job.Cook);
        System.out.println(workerRepository.findByUsername("TimCook").get().getToken());
        if (cook.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<GetOrderCook> orders = orderRepository.findAllByStatusIn(Arrays.asList(Status.InProgress, Status.Ordered),
                GetOrderCook.class);
        return new ResponseEntity<>(new Orders(orders), HttpStatus.OK);
    }

}
