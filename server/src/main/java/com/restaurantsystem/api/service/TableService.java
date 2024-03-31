package com.restaurantsystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.manager.PostTable;

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
        var tableOptional = tableRepository.findByXAndY(table.getX(), table.getY());
        if (tableOptional.isPresent()) {
            var tableOld = tableOptional.get();
            tableOld.setNumSeats(table.getNumSeats());
            tableOld.setNumber(table.getNumber());
            tableOld.setWaiter(table.getWaiter());
            tableOld.setOccupied(table.isOccupied());
            tableOld.setActive(table.isActive());
            tableRepository.save(tableOld);
        } else {
            tableRepository.save(table);
        }
    }

    @Transactional
    public void setTable(PostTable postTable) {
        // Set all tables as inactive
        var newTable = new Table(postTable.x(), postTable.y());
        newTable.setActive(postTable.isActive());
        newTable.setNumber(postTable.number());
        newTable.setNumSeats(postTable.numSeats());
        newTable.setOccupied(postTable.isOccupied());
        var waiter = workerRepository.findById(postTable.waiter());
        if (waiter.isEmpty())
            newTable.setWaiter(null);
        else
            newTable.setWaiter(waiter.get());
        addTable(newTable);
    }
}
