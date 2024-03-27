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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.controller.WaiterControllerTests.WaiterTable.ListTables;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Order.Status;
import com.restaurantsystem.api.repos.OrderRepository;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.ListOfItems;
import com.restaurantsystem.api.shared.TestSharedItem;
import com.restaurantsystem.api.shared.waiter.PostOrderWaiter;

public class WaiterControllerTests extends ControllerParentTests {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private OrderRepository orderRepository;

    public WaiterControllerTests() {
        login = DatabasePopulate.Waiter1;
        path = "/waiter";
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
    void getOrder() throws Exception {
        var orders = getMockMvcResultType("/order", ListOfOrders.class);
        assertTrue(orders.orders.size() > 0);
        Optional<String> hostToken = authenticationService.login(DatabasePopulate.Host1.username(),
                DatabasePopulate.Host1.password());
        assertTrue(hostToken.isPresent());
        getMockMvcBuilderWithToken("/order", hostToken.get())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
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
    void getTables() throws Exception {
        var tables = getMockMvcResultType("/tables", ListTables.class);
        assertTrue(tables.tables.size() > 3);
    }

    @Test
    void addOrder() throws Exception {
        String orderDetails = objectMapper.writeValueAsString(new PostOrderWaiter(Arrays.asList(1, 2), 1));
        var orderId = postMockMvcResultType("/addOrder", orderDetails, Integer.class);
        assertTrue(orderId > 0);
        var waiter = workerRepository.findByToken(token);
        assertTrue(waiter.isPresent());
        var order = orderRepository.findById(orderId);
        assertTrue(order.isPresent());
        assertEquals(order.get().getStatus(), Status.Ordered);
        var items = order.get().getItems();
        assertEquals(items.get(0).getId(), 1);
        assertEquals(items.get(1).getId(), 2);
        assertTrue(order.get().getTimeOrdered().before(new Date()));
        assertTrue(order.get().getTimeOrdered().after(new Date(new Date().getTime() - 10 * 1000)));
        var table = tableRepository.findById(1);
        assertTrue(table.isPresent());
        assertTrue(table.get().isOccupied());
    }

    @Test
    void completeOrder() throws Exception {
        int cookedOrderId = 3;
        postMockMvcResult("/completeOrder", Integer.toString(cookedOrderId));
        assertTrue(orderRepository.existsById(cookedOrderId));
        assertEquals(orderRepository.findById(cookedOrderId).get().getStatus(), Status.Delivered);
        Optional<Table> table = tableRepository.findById(4);
        assertTrue(table.isPresent());
        assertFalse(table.get().isOccupied());
        int orderedOrderId = 1;
        postMockMvcBuilder("/completeOrder", Integer.toString(orderedOrderId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        int inProgressOrderId = 2;
        postMockMvcBuilder("/completeOrder", Integer.toString(inProgressOrderId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        int deliveredOrderId = 4;
        postMockMvcBuilder("/completeOrder", Integer.toString(deliveredOrderId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void cancelOrder() throws Exception {
        postMockMvcResult("/cancelOrder", "1");
        assertEquals(orderRepository.findById(1).get().getStatus(), Status.Canceled);
        assertFalse(tableRepository.findById(2).get().isOccupied());
    }

    @Test
    void getItems() throws Exception {
        var items = getMockMvcResultType("/items", ListOfItems.class);
        assertTrue(items.items().size() > 0);
    }
}
