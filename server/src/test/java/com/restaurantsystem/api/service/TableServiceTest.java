package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.BaseTests;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.repos.TableRepository;

@Rollback(true)
@Transactional
public class TableServiceTest extends BaseTests {
    @Autowired
    TableService tableService;
    @Autowired
    TableRepository tableRepository;

    @Test
    void addTable() {
        Table table = new Table(10, 10);
        table.setActive(true);
        table.setNumber(785432);
        tableService.addTable(table);
        assertTrue(tableRepository.findByXAndY(10, 10).isPresent());
        table.setNumber(645324);
        tableService.addTable(table);
        var tableOptional = tableRepository.findByXAndY(10, 10);
        assertTrue(tableOptional.isPresent());
        assertEquals(tableOptional.get().getNumber(), 645324);
    }
}
