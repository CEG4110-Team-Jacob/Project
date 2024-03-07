package com.restaurantsystem.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.interfaces.AuthenticationService;
import com.restaurantsystem.api.shared.enums.Job;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    WorkerRepository workerRepository;

    @Override
    public Optional<String> login(String username, String password) {
        if (username.equals("test") && password.equals(""))
            return Optional.of("hufhiohwef");
        return Optional.empty();
    }

    @Override
    public Optional<Worker> authenticate(String token) {
        if (!token.equals("hufhiohwef"))
            return Optional.empty();
        Worker worker = new Worker();
        worker.setAge(10);
        worker.setId(0);
        worker.setJob(Job.Waiter);
        worker.setName("LOL");
        worker.setToken("hufhiohwef");
        return Optional.of(worker);
    }

}
