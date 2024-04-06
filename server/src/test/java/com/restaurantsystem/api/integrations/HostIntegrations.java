package com.restaurantsystem.api.integrations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback(true)
@Transactional
public class HostIntegrations extends BaseIntegrationTests {
    @Test
    void changeTableStatus() {
        // Occupy
        assertTrue(hostController.occupy(hostT, 1).getStatusCode().is2xxSuccessful());
        assertTrue(hostController.occupy(hostT, 1).getStatusCode().isError());

        var waiterTables = waiterController.getTables(waiterT);
        assertTrue(waiterTables.getStatusCode().is2xxSuccessful());
        var waiterTable = waiterTables.getBody().tables().stream().filter(table -> table.getId() == 1).findAny();
        assertTrue(waiterTable.isPresent());
        assertTrue(waiterTable.get().getIsOccupied());
        // Vacant
        assertTrue(hostController.vacant(hostT, 1).getStatusCode().is2xxSuccessful());

        var waiterTables2 = waiterController.getTables(waiterT);
        assertTrue(waiterTables2.getStatusCode().is2xxSuccessful());
        var waiterTable2 = waiterTables2.getBody().tables().stream().filter(table -> table.getId() == 1).findAny();
        assertTrue(waiterTable2.isPresent());
        assertFalse(waiterTable2.get().getIsOccupied());
    }
}
