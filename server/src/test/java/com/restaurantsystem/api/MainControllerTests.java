package com.restaurantsystem.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.restaurantsystem.api.controller.ControllerParentTests;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.shared.ListOfItems;

import jakarta.transaction.Transactional;

@Transactional
@Rollback(true)
public class MainControllerTests extends ControllerParentTests {

    public MainControllerTests() {
        path = "";
        login = DatabasePopulate.Waiter1;
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login").param("uname", DatabasePopulate.Waiter1.username())
                .param("passwd", DatabasePopulate.Waiter1.password()))
                .andExpectAll(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void logout() throws Exception {
        postMockMvcResult("/logout", "");
        assertTrue(authenticationService.authenticate(token).isEmpty());
    }

    record WorkerDetails(int id, String firstName, String lastName, int age, Job job) {
    }

    @Test
    void getDetails() throws Exception {
        var details = getMockMvcResultType("/getDetails", WorkerDetails.class);
        assertEquals(details.job, Job.Waiter);
    }

    @Test
    void getItems() throws Exception {
        var items = getMockMvcResultType("/items", ListOfItems.class);
        assertTrue(items.items().size() > 0);
    }
}
