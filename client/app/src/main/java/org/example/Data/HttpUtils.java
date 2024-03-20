package org.example.Data;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

public class HttpUtils {
    public static final String SERVER_URL = "http://localhost:8081";
    private static URI URI;
    private static final RestClient restClient = RestClient.create();

    static {
        try {
            URI = new URI(SERVER_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Optional<String> login(String uname, String password) {
        String query = "uname=" + uname + "&passwd=" + password;
        try {
            ResponseEntity<String> response = restClient.get().uri(URI + "/login?" + query).retrieve()
                    .toEntity(String.class);
            return Optional.of(response.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}
