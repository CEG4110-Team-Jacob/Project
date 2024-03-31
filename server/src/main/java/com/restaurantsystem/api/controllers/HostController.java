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

@RestController
@RequestMapping("/host")

public class HostController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    TableRepository tableRepository;

    public record GetHostTables(List<TableProjectionHost> tables) {
    }

    @GetMapping("/tables")
    public ResponseEntity<GetHostTables> getTables(@RequestParam String t) {
        var worker = authenticationService.hasJobAndAuthenticate(t, Job.Host);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        var tables = tableRepository.findAllByIsActive(true, TableProjectionHost.class);
        return ResponseEntity.ok().body(new GetHostTables(tables));
    }

    @PostMapping("/occupy")
    @Transactional
    public ResponseEntity<Boolean> occupy(@RequestParam String t, @RequestBody int id) {
        var worker = authenticationService.hasJobAndAuthenticate(t, Job.Host);
        if (worker.isEmpty())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        var table = tableRepository.findById(id);
        if (table.isEmpty() || !table.get().isActive() || table.get().isOccupied())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        table.get().setOccupied(true);
        tableRepository.save(table.get());
        return ResponseEntity.ok().body(true);
    }

}
