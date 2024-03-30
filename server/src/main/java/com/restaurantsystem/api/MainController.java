package com.restaurantsystem.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.all.ListOfItems;
import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.all.WorkerProjection;

/**
 * A controller for general stuff. i.e. login and logout
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class MainController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    OrderRepository orderRepository;

    /**
     * Tries to log in
     * 
     * @param uname    username
     * @param password password
     * @return A response with a token if authenticated else a status code.
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(value = "uname") String uname,
            @RequestParam(value = "passwd") String password) {
        Optional<String> token = authenticationService.login(uname, password);
        if (token.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>(token.get(), HttpStatus.OK);
    }

    /**
     * Gets the Job of a worker
     * 
     * @param t token
     * @return Job
     */
    @GetMapping("/getDetails")
    public ResponseEntity<WorkerProjection> getDetails(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.authenticate(t);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        WorkerProjection workerProjection = workerRepository.findById(worker.get().getId(), WorkerProjection.class)
                .get();
        return new ResponseEntity<>(workerProjection, HttpStatus.OK);
    }

    /**
     * Logs the user out
     * 
     * @param t token
     */
    @PostMapping("/logout")
    public void logout(@RequestParam String t) {
        authenticationService.logout(t);
    }

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/items")
    public ResponseEntity<ListOfItems> getItems(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.authenticate(t);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<SharedItem> items = itemRepository.findAllByIsActive(true, SharedItem.class);
        return new ResponseEntity<ListOfItems>(new ListOfItems(items), HttpStatus.OK);
    }

    /**
     * Driver
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainController.class, args);
    }
}
