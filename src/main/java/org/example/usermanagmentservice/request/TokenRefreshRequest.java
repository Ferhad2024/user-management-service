package org.example.usermanagmentservice.request;

import jakarta.validation.constraints.NotBlank;
public record TokenRefreshRequest(

        String refreshtoken

) {}

