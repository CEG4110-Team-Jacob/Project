package com.restaurantsystem.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.all.ListOfItems;
import com.restaurantsystem.api.shared.all.SharedItem;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.ManagerViewWorker;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for managers
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    WorkerRepository workerRepository;

    /**
     * Gets all the items on the menu
     * 
     * @param t token
     * @return Items
     */
    @GetMapping("/items")
    public ResponseEntity<ListOfItems> getItems(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<SharedItem> items = itemRepository.findAllBy(SharedItem.class);
        return new ResponseEntity<ListOfItems>(new ListOfItems(items), HttpStatus.OK);
    }

    /**
     * Creates a worker
     * 
     * @param accountDetails
     * @param t              token
     */
    @PostMapping("/createWorker")
    public ResponseEntity<Void> createWorker(@RequestBody PostCreateAccount accountDetails,
            @RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<Worker> newWorker = authenticationService.addWorker(accountDetails);
        if (newWorker.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Creates an item to add to the menu
     * 
     * @param itemDetails
     * @param t           token
     */
    @PostMapping("/addItem")
    @Transactional
    public ResponseEntity<Void> addItem(@RequestBody AddItem itemDetails,
            @RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Item item = new Item();
        item.setDescription(itemDetails.description());
        item.setName(itemDetails.name());
        item.setInStock(itemDetails.inStock());
        item.setPrice(itemDetails.price());
        item.setType(itemDetails.type());
        item = itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/workers")
    public ResponseEntity<ManagerViewWorker.ListWorkers> getWorkers(@RequestParam String t) {
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<ManagerViewWorker> workers = workerRepository.findAllBy(ManagerViewWorker.class);
        return new ResponseEntity<>(new ManagerViewWorker.ListWorkers(workers), HttpStatus.OK);
    }
}
