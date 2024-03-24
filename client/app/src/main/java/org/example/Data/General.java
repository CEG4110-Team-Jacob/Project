package org.example.Data;

import java.net.URI;
import java.util.Optional;

import org.example.Data.Utils.GetMethods;
import org.example.Data.records.WorkerDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

public class General {
    public static final String SERVER_URL = "http://localhost:8080";
    public static URI URI;
    public static final RestClient restClient = RestClient.create();

    public static GetMethods<WorkerDetails> details = new GetMethods<>("/getDetails", WorkerDetails.class);

    static {
        try {
            URI = new URI(SERVER_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reset() {
        details.reset();
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

    public static void logout() {
        if (Data.token == null || Data.token.isEmpty())
            return;
        String query = "t=" + Data.token;
        Data.deleteData();
        try {
            restClient.post().uri(URI + "/logout?" + query).retrieve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Optional<WorkerDetails> getDetails() {
        return details.get();
    }

    public static Optional<WorkerDetails> setDetails() {
        return details.set();
    }

}
