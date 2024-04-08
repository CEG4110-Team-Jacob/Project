package com.restaurantsystem.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.controllers.ManagerController.PostMessageSend;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.ItemRepository;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.ManagerViewWorker.ListWorkers;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;
import com.restaurantsystem.api.shared.manager.PostTable;

@Transactional
@Rollback(true)
public class ManagerControllerTests extends ControllerParentTests {
    /**
     * PostChangeItem
     */
    public record PostChangeItem(int id, AddItem details) {
    }

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TableRepository tableRepository;

    public ManagerControllerTests() {
        this.login = DatabasePopulate.Manager1;
        this.path = "/manager";
    }

    @Test
    void createAccount() throws Exception {
        var data = toJson(new PostCreateAccount("baba", "Not", 30, Job.Host, "hsdagai", "asdfhuas"));
        postMockMvcResult("/createWorker", data);
        assertTrue(workerRepository.existsByUsername("hsdagai"));
    }

    @Test
    void addItem() throws Exception {
        var data = toJson(new AddItem("asda", "gasd", 200, true, ItemType.Food));
        postMockMvcResult("/addItem", data);
        assertTrue(itemRepository.existsByName("asda"));
    }

    @Test
    void getWorkers() throws Exception {
        var workers = getMockMvcResultType("/workers", ListWorkers.class);
        assertTrue(workers.workers().size() > 2);
    }

    @Test
    void deleteWorker() throws Exception {
        postMockMvcResult("/deleteWorker", "1");
        assertFalse(workerRepository.findById(1).get().isActive());
    }

    @Test
    void changeItem() throws Exception {
        var data = toJson(new PostChangeItem(1, new AddItem("guhd", "inasgf", 37825, false, ItemType.Food)));
        postMockMvcResult("/changeItem", data);
        var newItem = itemRepository.findById(1).get();
        assertEquals(newItem.getName(), "guhd");
        assertEquals(newItem.getDescription(), "inasgf");
        assertEquals(newItem.getPrice(), 37825);
        assertFalse(newItem.isInStock());
        assertEquals(newItem.getType(), ItemType.Food);
    }

    @Test
    void deleteItem() throws Exception {
        postMockMvcResult("/deleteItem", "1");
        assertFalse(itemRepository.findById(1).get().isActive());
    }

    @Test
    void setTable() throws Exception {
        var prev = tableRepository.findAllByIsActive(true, Table.class).size();
        var table = new PostTable(10, 10, 0, 10, 5, false, true);
        postMockMvcResult("/setTable", toJson(table));
        assertTrue(tableRepository.findAllByIsActive(true, Table.class).size() - prev == 1);
    }

    // ChatGPT generation
    @Test
    void sendMessage() throws Exception {
        var payload = new PostMessageSend("Hello Waiter", 1);
        postMockMvcResult("/message", toJson(payload));

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient client = new WebSocketStompClient(webSocketClient);
        client.setMessageConverter(new StringMessageConverter());
        client.setTaskScheduler(new SimpleAsyncTaskScheduler());

        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
        StompSession stompSession;
        try {
            stompSession = client
                    .connectAsync(getWSUrl(), new StompSessionHandlerAdapter() {
                    }).get(10, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            // Handle the execution exception
            Throwable cause = e.getCause();
            cause.printStackTrace(); // Print the cause of the exception for debugging
            fail();
            return;
        }
        stompSession.subscribe("/topic/message/1", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                blockingQueue.offer((String) payload);
            }
        });
        postMockMvcResult("/message", toJson(payload));
        String message = blockingQueue.poll(10, TimeUnit.SECONDS);
        System.out.println(message);
        assertEquals(message, "Hello Waiter");
    }
}
