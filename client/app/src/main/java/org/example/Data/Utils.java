package org.example.Data;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
    public static String DEFAULT_QUERY = "t=" + Data.token;

    public static class GetMethods<T> {

        public interface SetFunc<T> {
            public void set(T v);
        }

        public interface GetFunc<T> {
            public T get();
        }

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
                e.printStackTrace();
            }
            return Optional.empty();
        }

        public Optional<T> get(String query) {
            if (getValue() != null)
                return Optional.of(getValue());
            return set(query);
        }

        public Optional<T> set() {
            return set(DEFAULT_QUERY);
        }

        public Optional<T> get() {
            return get(DEFAULT_QUERY);
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
}
