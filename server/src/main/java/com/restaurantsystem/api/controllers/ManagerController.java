package com.restaurantsystem.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.service.TableService;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.ManagerViewWorker;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;
import com.restaurantsystem.api.shared.manager.PostTable;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/manager")
public class ManagerController {

    /**
     * Post Body that changes an item's values
     */
    public record ChangeItem(int id, AddItem details) {
    }

    /**
     * Post Body that sends a message to a worker
     */
    public record PostMessageSend(String content, int id) {
    }

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    TableService tableService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * Creates a worker
     * 
     * @param accountDetails
     * @param t              token
     * @return Response
     */
    @PostMapping("/createWorker")
    public ResponseEntity<Boolean> createWorker(@RequestBody PostCreateAccount accountDetails,
            @RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Add the worker
        Optional<Worker> newWorker = authenticationService.addWorker(accountDetails);
        if (newWorker.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(true);
    }

    /**
     * Sets a table's data
     * 
     * @param tableData Table's new data
     * @param t         token
     * @return Response
     */
    @PostMapping("/setTable")
    public ResponseEntity<Boolean> setTable(@RequestBody PostTable tableData, @RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Update the table
        tableService.setTable(tableData);
        return ResponseEntity.ok().body(true);
    }

    /**
     * Marks a worker is inactive
     * 
     * @param t        token
     * @param workerId
     * @return Response
     */
    @PostMapping("/deleteWorker")
    public ResponseEntity<Boolean> deleteWorker(@RequestParam String t, @RequestBody Integer workerId) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Update worker
        authenticationService.deleteWorker(workerId);
        return ResponseEntity.ok().body(true);
    }

    /**
     * Creates an item to add to the menu
     * 
     * @param itemDetails
     * @param t           token
     */
    @PostMapping("/addItem")
    @Transactional
    public ResponseEntity<Boolean> addItem(@RequestBody AddItem itemDetails,
            @RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Create the new item
        Item item = new Item();
        item.setDescription(itemDetails.description());
        item.setName(itemDetails.name());
        item.setInStock(itemDetails.inStock());
        item.setPrice(itemDetails.price());
        item.setType(itemDetails.type());
        item = itemRepository.save(item);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Marks an item as inactive
     * 
     * @param t  token
     * @param id item id
     * @return Response
     */
    @PostMapping("/deleteItem")
    @Transactional
    public ResponseEntity<Boolean> deleteItem(@RequestParam String t, @RequestBody int id) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the item
        var itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty())
            return ResponseEntity.badRequest().body(false);
        // Update the item
        var item = itemOptional.get();
        item.setActive(false);
        itemRepository.save(item);
        return ResponseEntity.ok().body(true);
    }

    /**
     * Changes an item's data
     * 
     * @param details item's details
     * @param t       token
     * @return Response
     */
    @PostMapping("/changeItem")
    @Transactional
    public ResponseEntity<Boolean> changeItem(@RequestBody ChangeItem details, @RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get Item
        var itemOptional = itemRepository.findById(details.id());
        if (itemOptional.isEmpty())
            return ResponseEntity.badRequest().body(false);
        // Update Item
        var item = itemOptional.get();
        item.setDescription(details.details().description());
        item.setInStock(details.details().inStock());
        item.setName(details.details().name());
        item.setPrice(details.details().price());
        item.setType(details.details().type());
        itemRepository.save(item);
        return ResponseEntity.ok().body(true);
    }

    /**
     * Gets the workers
     * 
     * @param t token
     * @return A list of workers
     */
    @GetMapping("/workers")
    public ResponseEntity<ManagerViewWorker.ListWorkers> getWorkers(@RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t, Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the workers
        List<ManagerViewWorker> workers = workerRepository.findAllByIsActive(true, ManagerViewWorker.class);
        return new ResponseEntity<>(new ManagerViewWorker.ListWorkers(workers), HttpStatus.OK);
    }

    /**
     * Send a message to a worker
     * 
     * @param message Message and Worker Id
     * @param t       token
     * @return Response
     */
    @PostMapping("/message")
    public ResponseEntity<Boolean> sendMessage(@RequestBody PostMessageSend message, @RequestParam String t) {
        // Authenticate
        Optional<Worker> worker = authenticationService.hasJobAndAuthenticate(t,
                Job.Manager);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Message to send
        var msg = worker.get().getFirstName() + " " + worker.get().getLastName() + " said\n" + message.content;
        // Send the message
        messagingTemplate.convertAndSend("/topic/message/" + message.id, msg);
        return ResponseEntity.ok().body(true);
    }
}
