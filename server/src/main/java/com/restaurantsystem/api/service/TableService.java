package com.restaurantsystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.manager.PostTable;

/**
 * A service that handles tables
 */
@Service
public class TableService {
    @Autowired
    TableRepository tableRepository;
    @Autowired
    WorkerRepository workerRepository;

    /**
     * Adds a table to the database
     * 
     * @param table
     */
    @Transactional
    public void addTable(Table table) {
        // Find the table located at position
        var tableOptional = tableRepository.findByXAndY(table.getX(), table.getY());
        // If one exists, update
        if (tableOptional.isPresent()) {
            var tableOld = tableOptional.get();
            tableOld.setNumSeats(table.getNumSeats());
            tableOld.setNumber(table.getNumber());
            tableOld.setWaiter(table.getWaiter());
            tableOld.setOccupied(table.isOccupied());
            tableOld.setActive(table.isActive());
            tableRepository.save(tableOld);
        } else {
            // Else create a new one
            tableRepository.save(table);
        }
    }

    /**
     * Updates or Creates a table
     * 
     * @param postTable Table Data
     */
    @Transactional
    public void setTable(PostTable postTable) {
        // Create new table
        var newTable = new Table(postTable.x(), postTable.y());
        newTable.setActive(postTable.isActive());
        newTable.setNumber(postTable.number());
        newTable.setNumSeats(postTable.numSeats());
        newTable.setOccupied(postTable.isOccupied());
        // Get the waiter for the table
        var waiter = workerRepository.findById(postTable.waiter());
        if (waiter.isEmpty())
            newTable.setWaiter(null);
        else
            newTable.setWaiter(waiter.get());
        addTable(newTable);
    }
}
