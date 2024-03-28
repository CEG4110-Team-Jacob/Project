package com.restaurantsystem.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.restaurantsystem.api.BaseTests;
import com.restaurantsystem.api.DatabasePopulate.Login;
import com.restaurantsystem.api.service.AuthenticationService;

public class ControllerParentTests extends BaseTests {

    @Autowired
    protected AuthenticationService authenticationService;

    protected String token;

    protected String path = "";

    protected Login login;

    @BeforeEach
    void login() {
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

    private MvcResult isOk(ResultActions result) throws Exception {
        return result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    protected MvcResult getMockMvcResult(String path2) throws Exception {
        return isOk(getMockMvcBuilder(path2));
    }

    private <T> T readValue(MvcResult result, Class<T> type) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), type);
    }

    protected <T> T getMockMvcResultType(String path2, Class<T> type) throws Exception {
        return readValue(getMockMvcResult(path2), type);
    }
}
