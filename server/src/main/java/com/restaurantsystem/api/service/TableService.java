package com.restaurantsystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.repos.TableRepository;

@Service
public class TableService {
    @Autowired
    TableRepository tableRepository;

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
            tableRepository.save(tableOld);
        } else {
            tableRepository.save(table);
        }
    }
}
