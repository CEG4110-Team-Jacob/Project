package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;

import com.restaurantsystem.api.data.Worker;

public interface WorkerRepository extends CrudRepository<Worker, Integer> {
    // TODO STUB
    default public String getToken(String uname, String password) {
        // Test User
        if (uname.equals("test") && password.equals(""))
            return "hufhiohwef";
        return null;
    }

    // TODO STUB
    default public Worker authenticate(String token) {
        if (token.equals("hufhiohwef"))
            return new Worker();
        return null;
    }
}
