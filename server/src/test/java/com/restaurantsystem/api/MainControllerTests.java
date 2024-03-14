package com.restaurantsystem.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.restaurantsystem.api.repos.WorkerRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
class MainControllerTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WorkerRepository workerRepository;

	@Test
	void contextLoads() {
		assertNotNull(restTemplate);
		assertNotNull(workerRepository);
	}

	@Test
	@Transactional
	void login() {
		String url = "http://localhost:" + port + "/";
		String query = "?uname=" + DatabasePopulate.Waiter1.username() + "&passwd="
				+ DatabasePopulate.Waiter1.password();
		ResponseEntity<String> response = restTemplate.getForEntity(url + "login" + query, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
