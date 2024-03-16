package com.restaurantsystem.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
import com.restaurantsystem.api.service.interfaces.AuthenticationService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({ DatabasePopulate.class })
class MainControllerTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@Test
	void contextLoads() {
		assertNotNull(restTemplate);
		assertNotNull(workerRepository);
	}

	private String getUrl() {
		return "http://localhost:" + port + "/";
	}

	@Test
	void login() {
		String query = "?uname=" + DatabasePopulate.Waiter1.username() + "&passwd="
				+ DatabasePopulate.Waiter1.password();
		ResponseEntity<String> response = restTemplate.getForEntity(getUrl() + "login" + query, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void logout() {
		Optional<String> t = authenticationService.login(DatabasePopulate.Waiter1.username(),
				DatabasePopulate.Waiter1.password());
		assertTrue(t.isPresent());
		restTemplate.postForEntity(getUrl() + "logout?t=" + t.get(), null, null);
		assertTrue(authenticationService.authenticate(t.get()).isEmpty());
	}
}
