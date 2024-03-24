package org.example.Data;

import java.net.URI;
import java.util.Optional;

import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;
import org.example.Data.records.WorkerDetails;
import org.springframework.web.client.RestClient;

public class General {
    public static final String SERVER_URL = "http://localhost:8080";
    public static URI URI;
    public static final RestClient restClient = RestClient.create();

    public static GetMethods<WorkerDetails> details = new GetMethods<>("/getDetails", WorkerDetails.class);
    public static GetMethods<String> token = new GetMethods<>("/login", String.class);
    public static PostMethods<Void, Void> logout = new PostMethods<>("/logout", Void.class);

    static {
        try {
            URI = new URI(SERVER_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reset() {
        details.reset();
        token.reset();
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
        Data.deleteData();
        logout.post(null);
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
