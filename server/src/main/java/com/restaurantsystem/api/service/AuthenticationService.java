package com.restaurantsystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantsystem.api.repos.WorkerRepository;

@Service
public class AuthenticationService {
    WorkerRepository userRepository;

    @Autowired
    public AuthenticationService(WorkerRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String username, String password) {
        return userRepository.getToken(username, password);
    }
}
