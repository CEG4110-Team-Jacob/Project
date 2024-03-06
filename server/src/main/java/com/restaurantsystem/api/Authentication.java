package com.restaurantsystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.repos.WorkerRepository;

@RestController
public class Authentication {
    @Autowired
    WorkerRepository userRepository;

    /**
     * Tries to log in
     * 
     * @param uname    username
     * @param password password
     * @return A response with a token if authenticated else a status code.
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(value = "uname") String uname,
            @RequestParam(value = "passwd") String password) {
        String token = userRepository.getToken(uname, password);
        if (token == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
