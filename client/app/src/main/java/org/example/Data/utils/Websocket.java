package org.example.Data.utils;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.example.Data.controllers.General;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * WebSocket
 */
public class Websocket<T> {

    private Consumer<T> consumer;
    private Class<T> type;
    private String path;
    private WebSocketClient webSocketClient = new StandardWebSocketClient();
    private WebSocketStompClient client;
    private StompSession stompSession;

    /**
     * 
     * @param consumer function to run when fired
     * @param type     return type
     * @param path     Websocket path
     */
    public Websocket(Consumer<T> consumer, Class<T> type, String path) {
        this.consumer = consumer;
        this.type = type;
        this.path = path;
    }

    /**
     * Start the websocket
     */
    public void start() {
        if (client != null)
            client.stop();
        // Create new client
        client = new WebSocketStompClient(webSocketClient);
        client.setMessageConverter(new StringMessageConverter());
        // Create session
        try {
            stompSession = client.connectAsync(General.WEBSOCKET_URL, new StompSessionHandlerAdapter() {
            }).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Subscribe
        stompSession.subscribe(path + General.getDetails().get().id(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload); // Hopefully the server returns the correct type
            }
        });
    }

    /**
     * Stops the WebSocket
     */
    public void stop() {
        if (client == null)
            return;
        client.stop();
        client = null;
    }
}
