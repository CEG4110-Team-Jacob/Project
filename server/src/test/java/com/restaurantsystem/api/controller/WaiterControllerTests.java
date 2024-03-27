package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.controller.WaiterControllerTests.WaiterTable.ListTables;
import com.restaurantsystem.api.data.Item;
import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.TestSharedItem;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WaiterControllerTests extends ControllerParentTests {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private OrderRepository orderRepository;

    public WaiterControllerTests() {
        login = DatabasePopulate.Waiter1;
        path = "waiter";
    }

    @Test
    void contextLoads() {
        assertNotNull(workerRepository);
        assertNotNull(orderRepository);
    }

    record ListOfOrders(List<OrderRecord> orders) {
        record OrderRecord(int id, List<TestSharedItem> items, Date timeOrdered, Status status,
                int totalPrice) {
        }
    }

    @Test
    void getOrder() {
        ResponseEntity<ListOfOrders> response = restTemplate
                .getForEntity(getUrl() + "order?t=" + token, ListOfOrders.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().orders.isEmpty());
        Optional<String> hostToken = authenticationService.login(DatabasePopulate.Host1.username(),
                DatabasePopulate.Host1.password());
        assertTrue(hostToken.isPresent());
        ResponseEntity<ListOfOrders> hostReponse = restTemplate
                .getForEntity(getUrl() + "order?t=" + hostToken, ListOfOrders.class);
        assertEquals(hostReponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    public record WaiterTable(
            int id,
            int number, WaiterDetails waiter, boolean isOccupied, int numSeats) {
        public record WaiterDetails(String firstName, String lastName) {
        }

        public record ListTables(List<WaiterTable> tables) {
        }
    }

    @Test
    void getTables() {
        ResponseEntity<ListTables> response = restTemplate.getForEntity(getUrl() + "tables?t=" + token,
                ListTables.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().tables.size() > 3);
    }

    @Test
    @Transactional
    void addOrder() {
        ResponseEntity<Integer> response = restTemplate.postForEntity(
                getUrl() + "addOrder?t=" + token, new PostOrderWaiter(Arrays.asList(1, 2), 1),
                Integer.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody() > 0);
        Optional<Worker> waiter = workerRepository.findByToken(token);
        assertTrue(waiter.isPresent());
        Optional<Order> order = orderRepository.findById(response.getBody());
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.Ordered);
        List<Item> items = order.get().getItems();
        assertEquals(items.get(0).getId(), 1);
        assertEquals(items.get(1).getId(), 2);
        assertTrue(order.get().getTimeOrdered().before(new Date()));
        assertTrue(order.get().getTimeOrdered().after(new Date(new Date().getTime() - 10 * 1000)));
        Optional<Table> table = tableRepository.findById(1);
        assertTrue(table.isPresent());
        assertTrue(table.get().isOccupied());
    }

    @Test
    void completeOrder() {
        int cookedOrderId = 3;
        ResponseEntity<String> cookedOrder = restTemplate.postForEntity(getUrl() + "completeOrder?t=" + token,
                cookedOrderId,
                String.class);
        assertEquals(cookedOrder.getStatusCode(), HttpStatus.OK);
        assertTrue(orderRepository.existsById(cookedOrderId));
        assertEquals(orderRepository.findById(cookedOrderId).get().getStatus(), Status.Delivered);
        Optional<Table> table = tableRepository.findById(4);
        assertTrue(table.isPresent());
        assertFalse(table.get().isOccupied());
        int orderedOrderId = 1;
        ResponseEntity<String> orderedOrder = restTemplate.postForEntity(getUrl() + "completeOrder?t=" + token,
                orderedOrderId,
                String.class);
        assertEquals(orderedOrder.getStatusCode(), HttpStatus.BAD_REQUEST);
        int inProgressOrderId = 2;
        ResponseEntity<String> inProgressOrder = restTemplate.postForEntity(
                getUrl() + "completeOrder?t=" + token,
                inProgressOrderId,
                String.class);
        assertEquals(inProgressOrder.getStatusCode(), HttpStatus.BAD_REQUEST);
        int deliveredOrderId = 4;
        ResponseEntity<String> deliveredOrder = restTemplate.postForEntity(
                getUrl() + "completeOrder?t=" + token,
                deliveredOrderId,
                String.class);
        assertEquals(deliveredOrder.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void cancelOrder() {
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl() + "cancelOrder?t=" + token,
                1,
                String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(orderRepository.findById(1).get().getStatus(), Status.Canceled);
        assertFalse(tableRepository.findById(2).get().isOccupied());
    }

    @Test
    void getItems() {
        ResponseEntity<ListOfItems> items = restTemplate.getForEntity(getUrl() + "items?t=" + token,
                ListOfItems.class);
        assertEquals(items.getStatusCode(), HttpStatus.OK);
        assertTrue(items.getBody().items().size() > 0);
    }
}
