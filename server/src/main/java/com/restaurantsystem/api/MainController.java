package com.restaurantsystem.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainController {

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
}
