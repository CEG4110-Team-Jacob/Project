package org.example.Data;

import java.net.URI;
import java.util.Optional;

import org.example.Data.records.WorkerDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

public class HttpUtils {
    public static final String SERVER_URL = "http://localhost:8080";
    public static URI URI;
    public static final RestClient restClient = RestClient.create();

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

    public static void logout() {
        if (Data.token == null || Data.token.isEmpty())
            return;
        String query = "t=" + Data.token;
        try {
            restClient.post().uri(URI + "/logout?" + query).retrieve();
            Data.token = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Optional<WorkerDetails> getDetails() {
        String query = "t=" + Data.token;
        try {
            ResponseEntity<WorkerDetails> job = restClient.get().uri(URI + "/getDetails?" + query).retrieve()
                    .toEntity(WorkerDetails.class);
            if (job.getStatusCode() != HttpStatus.OK)
                return Optional.empty();
            if (!job.hasBody())
                return Optional.empty();
            return Optional.of(job.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
