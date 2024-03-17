package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.shared.ListOfItems;

public class ManagerControllerTests extends ControllerParentTests {
    public ManagerControllerTests() {
        this.login = DatabasePopulate.Manager1;
        this.path = "manager";
    }

    @Test
    void getItems() {
        ResponseEntity<ListOfItems> items = restTemplate.getForEntity(getUrl() + "items?t=" + token,
                ListOfItems.class);
        assertEquals(items.getStatusCode(), HttpStatus.OK);
        assertTrue(items.getBody().items().size() > 0);
    }
}
