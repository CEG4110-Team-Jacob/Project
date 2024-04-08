package com.restaurantsystem.api.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.data.Item.ItemType;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.shared.manager.AddItem;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;
import com.restaurantsystem.api.shared.manager.PostTable;
import com.restaurantsystem.api.controllers.ManagerController.ChangeItem;
import com.restaurantsystem.api.controllers.ManagerController.PostMessageSend;

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

    @Test
    void createWorker() {
        var username = "hidofg";
        var password = "iggnehrh";
        var workerPost = new PostCreateAccount("hoig", "dguiwe", 30, Job.Waiter, username, password);

        assertTrue(managerController.createWorker(workerPost, managerT).getStatusCode().is2xxSuccessful());
        Optional<String> loginToken = authenticationService.login(username, password);
        assertTrue(loginToken.isPresent());
        assertTrue(waiterController.getOrder(loginToken.get()).getStatusCode().is2xxSuccessful());
    }

    @Test
    void setTable() {
        var tablePost = new PostTable(0, 0, 1, 1232, 2, false, true);
        assertTrue(managerController.setTable(tablePost, managerT).getStatusCode().is2xxSuccessful());

        var waiterTables = waiterController.getTables(waiterT);
        assertTrue(waiterTables.getStatusCode().is2xxSuccessful());
        var waiterTable = waiterTables.getBody().tables().stream().filter(table -> table.getId() == 1).findAny();
        assertTrue(waiterTable.isPresent());
        assertTrue(waiterTable.get().getNumber() == 1232);
    }

    @Test
    void deleteItem() {
        assertTrue(managerController.deleteItem(managerT, 1).getStatusCode().is2xxSuccessful());

        var items = mainController.getItems(managerT);
        assertTrue(items.getStatusCode().is2xxSuccessful());
        var item = items.getBody().items().stream().filter(i -> i.getId() == 1).findAny();
        assertTrue(item.isEmpty());
    }

    @Test
    void changeItem() {
        var postItem = new ChangeItem(1, new AddItem("grwe", "bghunyer", 1, false, ItemType.Beverage));
        assertTrue(managerController.changeItem(postItem, managerT).getStatusCode().is2xxSuccessful());

        var items = mainController.getItems(managerT);
        assertTrue(items.getStatusCode().is2xxSuccessful());
        var item = items.getBody().items().stream().filter(i -> i.getId() == 1).findAny();
        assertTrue(item.isPresent());
        assertTrue(item.get().getName().equals("grwe"));
    }

    @Test
    void sendMessage() throws Exception {
        var payload = new PostMessageSend("Hello Waiter", 1);

        client.setTaskScheduler(new SimpleAsyncTaskScheduler());

        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
        StompSession stompSession;
        try {
            stompSession = client
                    .connectAsync(getWSUrl(), new StompSessionHandlerAdapter() {
                    }).get(10, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
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
        managerController.sendMessage(payload, managerT);
        String message = blockingQueue.poll(10, TimeUnit.SECONDS);
        Scanner scanner = new Scanner(message);
        scanner.nextLine();
        String line = scanner.nextLine();
        assertEquals(line, "Hello Waiter");
        scanner.close();
    }
}
