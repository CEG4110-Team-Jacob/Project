package com.restaurantsystem.api.service.interfaces;

import java.util.Optional;

import com.restaurantsystem.api.data.Worker;

public interface AuthenticationService {
    public Optional<String> login(String username, String password);

    public Optional<Worker> authenticate(String token);
}
