package com.restaurantsystem.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.BaseTests;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.shared.manager.PostTables;
import com.restaurantsystem.api.shared.manager.PostTables.PostTable;

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

    @Test
    void setTables() {
        var table = new PostTable(10, 10, 0, 10, 5);
        tableService.setTables(new PostTables(new HashSet<>(Arrays.asList(table))));
        assertTrue(tableRepository.findAllByIsActive(true, Table.class).size() == 1);
    }
}
