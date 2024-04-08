package org.example.Data.utils;

import java.io.IOException;
import java.util.Optional;

import org.example.Data.controllers.General;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClient.RequestBodySpec;

/**
 * Post Request
 */
public class PostMethods<T, V> {
    private String path;
    private Class<V> returnType;

    /**
     * 
     * @param path       API path
     * @param returnType Return Type
     */
    public PostMethods(String path, Class<V> returnType) {
        this.path = path;
        this.returnType = returnType;
    }

    /**
     * Post Request
     * 
     * @param body
     * @param query
     * @return return
     */
    public Optional<V> post(T body, String query) {
        // Create the request
        RequestBodySpec responseInit = General.restClient.post()
                .uri(General.URI + path + "?" + query)
                .body(body);
        try {
            // Send and get the response
            ResponseEntity<V> response = responseInit
                    .retrieve().onStatus((status) -> status.isError(), (a, b) -> {
                        throw new IOException();
                    })
                    .toEntity(returnType);
            // Check the response
            if (!response.getStatusCode().is2xxSuccessful())
                return Optional.empty();
            if (response.hasBody())
                return Optional.of(response.getBody());
            return Optional.empty();
        } catch (RestClientException e) {
        }
        return Optional.empty();
    }

    public Optional<V> post(T body) {
        return post(body, Utils.DEFAULT_QUERY());
    }
}