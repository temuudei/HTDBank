package learn.htdbank.domain;

import java.util.ArrayList;
import java.util.List;

    public class Result<T> {

        private final ArrayList<String> messages = new ArrayList<>();
        private T payload;

        public boolean isSuccess() {
            return messages.size() == 0;
        }

        public T getPayload() {
            return payload;
        }

        public void setPayload(T payload) {
            this.payload = payload;
        }

        public List<String> getMessages() {
            return new ArrayList<>(messages);
        }

        public void addErrorMessage(String message) {
            messages.add(message);
        }

    }
