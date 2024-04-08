package com.restaurantsystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base Class for basic tests
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BaseTests {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Converts an Object to Json
     * 
     * @param o object
     * @return json representation of object
     * @throws Exception Cannot convert
     */
    protected String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

}
