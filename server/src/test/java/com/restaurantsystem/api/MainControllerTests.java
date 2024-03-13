package com.restaurantsystem.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.AfterTransaction;

import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.repos.WorkerRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MainControllerTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WorkerRepository workerRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	void contextLoads() {
		assertNotNull(restTemplate);
		assertNotNull(workerRepository);
	}

	@BeforeTransaction
	void populate() {
		Worker worker = new Worker();
		worker.setUsername("test");
		worker.setPasswordHash(passwordEncoder.encode("test"));
		workerRepository.save(worker);
	}

	@AfterTransaction
	void depopulate() {
		workerRepository.deleteAll();
	}

	@Test
	@Transactional
	void login() {
		String url = "http://localhost:" + port + "/";
		String query = "?uname=test&passwd=test";
		ResponseEntity<String> response = restTemplate.getForEntity(url + "login" + query, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
