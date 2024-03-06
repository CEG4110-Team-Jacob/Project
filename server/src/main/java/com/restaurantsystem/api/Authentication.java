package com.restaurantsystem.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authentication {
    // TODO STUB
    /**
     * Authenticates a worker
     * 
     * @param uname    username
     * @param password password
     * @return a token that the worker will use to access the APIs
     */
    private String authenticateWorker(String uname, String password) {
        // Test User
        if (uname.equals("test") && password.equals(""))
            return "hufhiohwef";
        return null;
    }

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
        String token = authenticateWorker(uname, password);
        if (token == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
