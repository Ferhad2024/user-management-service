package org.example.usermanagmentservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRequest(

        @NotBlank(message = "Username boş ola bilməz")
        String username,

        @NotBlank(message = "Password boş ola bilməz")
        @Size(min = 8, max = 128, message = "Password 8–128 simvol arasında olmalıdır")
        String password

) {}
