package org.example.Data.controllers;

import java.net.URI;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.example.Data.Data;
import org.example.Data.records.Item;
import org.example.Data.records.Item.ListItems;
import org.example.Data.utils.GetMethods;
import org.example.Data.utils.PostMethods;
import org.example.Data.utils.Websocket;
import org.example.Data.records.WorkerDetails;
import org.springframework.web.client.RestClient;

/**
 * General APIs
 * <a
 * href=https://github.com/CEG4110-Team-Jacob/Project/wiki/Server#general>Documentation</a>
 */
public class General {
    // Base Server URL
    private static final String URL_BASE = "localhost:8080";

    public static final String SERVER_URL = "http://" + URL_BASE;
    public static final String WEBSOCKET_URL = "ws://" + URL_BASE + "/websocket";
    public static URI URI;
    public static final RestClient restClient = RestClient.create();

    public static GetMethods<WorkerDetails> details = new GetMethods<>("/getDetails", WorkerDetails.class);
    public static GetMethods<String> token = new GetMethods<>("/login", String.class);
    public static PostMethods<String, Void> logout = new PostMethods<>("/logout", Void.class);
    private static GetMethods<Item.ListItems> items = new GetMethods<>("/items", Item.ListItems.class);
    private static Websocket<String> messageWS = new Websocket<>((msg) -> {
        if (msg == null)
            return;
        JOptionPane.showMessageDialog(null, msg);
    }, String.class, "/topic/message/");

    /**
     * Create the URI to the server
     */
    static {
        try {
            URI = new URI(SERVER_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Optional<ListItems> setItems() {
        return items.set();
    }

    public static void reset() {
        details.reset();
        token.reset();
        items.reset();
        messageWS.stop();
    }

    public static void messageStart() {
        messageWS.start();
    }

    public static Optional<String> login(String uname, String password) {
        if (uname.isBlank() || password.isBlank())
            return Optional.empty();
        String query = "uname=" + uname + "&passwd=" + password;
        return token.set(query);
    }

    public static void logout() {
        if (getToken().isEmpty())
            return;
        logout.post("");
        Data.deleteData();
    }

    public static Optional<WorkerDetails> getDetails() {
        return details.get();
    }

    public static Optional<WorkerDetails> setDetails() {
        return details.set();
    }

    public static Optional<String> getToken() {
        return token.get("t=false");
    }

}
