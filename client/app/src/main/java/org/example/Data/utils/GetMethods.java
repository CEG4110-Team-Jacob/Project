package org.example.Data.utils;

import java.util.Optional;

import org.example.Data.controllers.General;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A GET Request
 */
public class GetMethods<T> {

    private String path;

    private Class<T> type;

    private T value;

    /**
     * 
     * @param path Path to the API
     * @param type Return Type
     */
    public GetMethods(String path, Class<T> type) {
        this.path = path;
        this.type = type;
    }

    public void reset() {
        value = null;
    }

    /**
     * Update the value
     * 
     * @param query
     * @return Value
     */
    public Optional<T> set(String query) {
        try {
            ResponseEntity<T> response = General.restClient.get()
                    .uri(General.URI + path + "?" + query).retrieve().toEntity(type);

            if (response.getStatusCode() != HttpStatus.OK)
                return Optional.empty();
            setValue(response.getBody());
            return Optional.of(response.getBody());
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Gets the value
     * 
     * @param query
     * @return value
     */
    public Optional<T> get(String query) {
        if (getValue() != null)
            return Optional.of(getValue());
        return set(query);
    }

    /**
     * Updates value using a token as the query
     * 
     * @return value
     */
    public Optional<T> set() {
        return set(Utils.DEFAULT_QUERY());
    }

    /**
     * Gets the value using a token
     * 
     * @return value
     */
    public Optional<T> get() {
        return get(Utils.DEFAULT_QUERY());
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}