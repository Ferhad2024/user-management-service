package org.example.usermanagmentservice.Exception;

import java.time.LocalDateTime;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String  message) {
        super(message);
    }
}
