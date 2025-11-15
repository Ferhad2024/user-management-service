package org.example.usermanagmentservice.response;

import java.time.LocalDateTime;

public record UserTxtResponse(
        Long id,
        String fileName,
        String status,
        LocalDateTime createdAt,
        String fileContent
) {}
