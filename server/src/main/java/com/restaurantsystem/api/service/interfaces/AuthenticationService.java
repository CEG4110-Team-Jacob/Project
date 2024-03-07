package com.restaurantsystem.api.service.interfaces;

import java.util.Optional;

public interface AuthenticationService {
    public Optional<String> login(String username, String password);
}
