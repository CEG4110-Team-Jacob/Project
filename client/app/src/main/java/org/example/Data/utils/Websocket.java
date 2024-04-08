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

public class Websocket<T> {

    private Consumer<T> consumer;
    private Class<T> type;
    private String path;
    private WebSocketClient webSocketClient = new StandardWebSocketClient();
    private WebSocketStompClient client;
    private StompSession stompSession;

    public Websocket(Consumer<T> consumer, Class<T> type, String path) {
        this.consumer = consumer;
        this.type = type;
        this.path = path;
    }

    public void start() {
        if (client != null)
            client.stop();
        client = new WebSocketStompClient(webSocketClient);
        client.setMessageConverter(new StringMessageConverter());
        try {
            stompSession = client.connectAsync(General.WEBSOCKET_URL, new StompSessionHandlerAdapter() {
            }).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
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

    public void stop() {
        if (client == null)
            return;
        client.stop();
        client = null;
    }
}
