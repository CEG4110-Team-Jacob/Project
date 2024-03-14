package com.restaurantsystem.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.service.AuthenticationServiceImpl;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class MainController {
	@Autowired
	AuthenticationServiceImpl authenticationService;
	@Autowired
	WorkerRepository workerRepository;
	@Autowired
	OrderRepository orderRepository;

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
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(token.get(), HttpStatus.OK);
	}

	/**
	 * Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
	}
}
