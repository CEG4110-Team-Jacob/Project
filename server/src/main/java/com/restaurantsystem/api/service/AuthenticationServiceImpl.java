package com.restaurantsystem.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    WorkerRepository workerRepository;

    @Override
    public Optional<String> login(String username, String password) {
        return Optional.of("hufhiohwef");
        // return workerRepository.getToken(username, password);
    }
}
