package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.repos.TableRepository;

@Rollback(true)
@Transactional
public class HostControllerTests extends ControllerParentTests {
    @Autowired
    TableRepository tableRepository;

    public record HostTables(List<HostTable> tables) {
        public record HostTable(int id, int number, boolean isOccupied, int numSeats, int x, int y) {
        }
    }

    public HostControllerTests() {
        path = "/host";
        login = DatabasePopulate.Host1;
    }

    @Test
    void getTables() throws Exception {
        var tables = getMockMvcResultType("/tables", HostTables.class);
        assertTrue(tables.tables().size() > 2);
    }

    @Test
    void occupy() throws Exception {
        postMockMvcResult("/occupy", "1");
        assertTrue(tableRepository.findById(1).get().isOccupied());
    }

    @Test
    void vacant() throws Exception {
        postMockMvcResult("/vacant", "6");
        assertTrue(!tableRepository.findById(6).get().isOccupied());
    }
}
