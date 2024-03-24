package org.example.Data;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
    public static class GetMethods<T> {

        private String path;

        private Class<T> type;

        private T value;

        public GetMethods(String path, Class<T> type) {
            this.path = path;
            this.type = type;
        }

        public void reset() {
            value = null;
        }

        public Optional<T> set(String query) {
            try {
                ResponseEntity<T> response = General.restClient.get()
                        .uri(General.URI + path + "?" + query).retrieve().toEntity(type);

                if (response.getStatusCode() != HttpStatus.OK)
                    return Optional.empty();
                setValue(response.getBody());
                return Optional.of(response.getBody());
            } catch (Exception e) {
                // e.printStackTrace();
            }
            return Optional.empty();
        }

        public Optional<T> get(String query) {
            if (getValue() != null)
                return Optional.of(getValue());
            return set(query);
        }

        public Optional<T> set() {
            return set(DEFAULT_QUERY());
        }

        public Optional<T> get() {
            return get(DEFAULT_QUERY());
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public Class<T> getType() {
            return type;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    public static class PostMethods<T, V> {
        private String path;
        private Class<V> returnType;

        public PostMethods(String path, Class<V> returnType) {
            this.path = path;
            this.returnType = returnType;
        }

        public Optional<V> post(T body, String query) {
            try {
                ResponseEntity<V> response = General.restClient.post().uri(General.URI + path + "?" + query).body(body)
                        .retrieve().toEntity(returnType);
                if (response.getStatusCode() != HttpStatus.OK)
                    return Optional.empty();
                if (response.hasBody())
                    return Optional.of(response.getBody());
                return Optional.empty();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }

        public Optional<V> post(T body) {
            return post(body, DEFAULT_QUERY());
        }
    }

    public static String DEFAULT_QUERY() {
        Optional<String> token = General.getToken();
        if (token.isEmpty())
            return "t=false";
        return "t=" + token.get();
    }
}
