package com.restaurantsystem.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.restaurantsystem.api.BaseTests;
import com.restaurantsystem.api.DatabasePopulate.Login;
import com.restaurantsystem.api.service.AuthenticationService;

/**
 * Base Class for Controllers
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerParentTests extends BaseTests {

    @Autowired
    protected AuthenticationService authenticationService;

    @LocalServerPort
    protected int port;

    // Token for the worker
    protected String token;

    // The general path of the controller
    protected String path = "";

    // Login information for the worker
    protected Login login;

    /**
     * Runs before each test and logs in
     */
    @BeforeEach
    protected void beforeEach() {
        token = authenticationService.login(login.username(), login.password()).get();
    }

    protected ResultActions getMockMvcBuilder(String path2) throws Exception {
        return getMockMvcBuilderWithToken(path2, token);
    }

    protected ResultActions postMockMvcBuilder(String path2, String content) throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders.post(path + path2).param("t", token)
                        .content(content).contentType(MediaType.APPLICATION_JSON));
    };

    protected MvcResult postMockMvcResult(String path2, String content) throws Exception {
        return isOk(postMockMvcBuilder(path2, content));
    };

    protected ResultActions getMockMvcBuilderWithToken(String path2, String token) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(path + path2).param("t", token).contentType(MediaType.APPLICATION_JSON));
    }

    protected <T> T postMockMvcResultType(String path2, String content, Class<T> type) throws Exception {
        return readValue(postMockMvcResult(path2, content), type);
    }

    protected MvcResult getMockMvcResult(String path2) throws Exception {
        return isOk(getMockMvcBuilder(path2));
    }

    protected <T> T getMockMvcResultType(String path2, Class<T> type) throws Exception {
        return readValue(getMockMvcResult(path2), type);
    }

    private MvcResult isOk(ResultActions result) throws Exception {
        return result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    private <T> T readValue(MvcResult result, Class<T> type) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), type);
    }
}
