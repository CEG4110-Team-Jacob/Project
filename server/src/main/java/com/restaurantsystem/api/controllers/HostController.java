package com.restaurantsystem.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.service.AuthenticationService;
import com.restaurantsystem.api.shared.host.TableProjectionHost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * A controller that handles the Host's Requests
 */
@RestController
@RequestMapping("/host")
public class HostController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    TableRepository tableRepository;

    // A list of tables the Host sees
    public record GetHostTables(List<TableProjectionHost> tables) {
    }

    /**
     * Gets the tables
     * 
     * @param t token
     * @return Tables
     */
    @GetMapping("/tables")
    public ResponseEntity<GetHostTables> getTables(@RequestParam String t) {
        // Authenticate
        var worker = authenticationService.hasJobAndAuthenticate(t, Job.Host);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the tables
        var tables = tableRepository.findAllByIsActive(true, TableProjectionHost.class);
        return ResponseEntity.ok().body(new GetHostTables(tables));
    }

    /**
     * Marks a table as occupied
     * 
     * @param t  token
     * @param id table id
     * @return Response
     */
    @PostMapping("/occupy")
    @Transactional
    public ResponseEntity<Boolean> occupy(@RequestParam String t, @RequestBody int id) {
        // Authenticate
        var worker = authenticationService.hasJobAndAuthenticate(t, Job.Host);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the table
        var table = tableRepository.findById(id);
        // Check if it's occupied
        if (table.isEmpty() || !table.get().isActive() || table.get().isOccupied())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // Update table
        table.get().setOccupied(true);
        tableRepository.save(table.get());
        return ResponseEntity.ok().body(true);
    }

    /**
     * Marks a table as unoccupied or vacant.
     * 
     * @param t  token
     * @param id table id
     * @return Response
     */
    @PostMapping("/vacant")
    @Transactional
    public ResponseEntity<Boolean> vacant(@RequestParam String t, @RequestBody int id) {
        // Authenticate
        var worker = authenticationService.hasJobAndAuthenticate(t, Job.Host);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // Get the table
        var table = tableRepository.findById(id);
        // Check if it's occupied
        if (table.isEmpty() || !table.get().isActive() || !table.get().isOccupied())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // update table
        table.get().setOccupied(false);
        tableRepository.save(table.get());
        return ResponseEntity.ok().body(true);
    }
}
