package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.repos.TableRepository;

/**
 * Tests for Host APIs
 */
@Rollback(true)
@Transactional
public class HostControllerTests extends ControllerParentTests {
    public record HostTables(List<HostTable> tables) {
        public record HostTable(int id, int number, boolean isOccupied, int numSeats, int x, int y) {
        }
    }

    @Autowired
    TableRepository tableRepository;

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
        postMockMvcBuilder("/occupy", "2").andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void vacant() throws Exception {
        postMockMvcResult("/vacant", "6");
        assertTrue(!tableRepository.findById(6).get().isOccupied());
    }
}
