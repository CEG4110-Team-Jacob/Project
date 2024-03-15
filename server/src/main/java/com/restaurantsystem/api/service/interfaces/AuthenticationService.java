package com.restaurantsystem.api.service.interfaces;

import java.util.Optional;

import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Worker.Job;

public interface AuthenticationService {
    /**
     * Logs in.
     * 
     * @param username
     * @param password
     * @return The token or none if invalid
     */
    public Optional<String> login(String username, String password);

    /**
     * Authenticates the token
     * 
     * @param token
     * @return The worker's data or none if token is invalid.
     */
    public Optional<Worker> authenticate(String token);

    public Optional<Worker> hasJobAndAuthenticate(String token, Job job);
}
