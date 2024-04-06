package com.restaurantsystem.api.integrations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;

@Rollback(true)
@Transactional
public class ManagerIntegrations extends BaseIntegrationTests {

    @Test
    void deleteWorker() {
        assertTrue(managerController.deleteWorker(managerT, 1).getStatusCode().is2xxSuccessful());

        assertTrue(
                authenticationService.login(DatabasePopulate.Waiter1.username(), DatabasePopulate.Waiter1.password())
                        .isEmpty());
        assertTrue(authenticationService.authenticate(waiterT).isEmpty());
    }
}
