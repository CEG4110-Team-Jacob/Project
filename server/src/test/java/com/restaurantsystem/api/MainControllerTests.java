package com.restaurantsystem.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.service.AuthenticationService;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests extends BaseTests {

    @Autowired
    AuthenticationService authenticationService;

    String token;

    @BeforeEach
    void start() throws Exception {
        token = authenticationService.login(DatabasePopulate.Waiter1.username(), DatabasePopulate.Waiter1.password())
                .get();
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login").param("uname", DatabasePopulate.Waiter1.username())
                .param("passwd", DatabasePopulate.Waiter1.password()))
                .andExpectAll(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/logout").param("t", token))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertTrue(authenticationService.authenticate(token).isEmpty());
    }

    record WorkerDetails(int id, String firstName, String lastName, int age, Job job) {
    }

    @Test
    void getDetails() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/getDetails").param("t", token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var details = objectMapper.readValue(response.getResponse().getContentAsString(), WorkerDetails.class);
        assertEquals(details.job, Job.Waiter);
    }
}
