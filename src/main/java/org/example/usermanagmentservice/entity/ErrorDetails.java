package org.example.usermanagmentservice.entity;

import java.time.LocalDateTime;


public record ErrorDetails(LocalDateTime time, Integer status, String error, String message) {
}