package org.example.usermanagmentservice.Exception;

public class AlreadyUserException extends RuntimeException{
    public AlreadyUserException(String message) {
        super(message);
    }
}
