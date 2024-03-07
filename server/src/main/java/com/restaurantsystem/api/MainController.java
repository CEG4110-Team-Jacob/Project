package com.restaurantsystem.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.service.AuthenticationServiceImpl;

@SpringBootApplication
@RestController
public class MainController {
	@Autowired
	AuthenticationServiceImpl authenticationService;

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello(@RequestParam(value = "name", defaultValue = "Hello World!") String name) {
		if (name.equals("wow")) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>(name, HttpStatus.OK);
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
		Optional<String> token = authenticationService.login(uname, password);
		if (token.isEmpty())
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		return new ResponseEntity<String>(token.get(), HttpStatus.OK);
	}
}
