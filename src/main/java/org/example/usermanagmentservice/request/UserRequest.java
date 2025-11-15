package org.example.usermanagmentservice.request;
import jakarta.validation.constraints.*;
import org.example.usermanagmentservice.Enum.RoleName;

public record UserRequest(

        @NotBlank(message = "Username boş ola bilməz")
        String username,

        @NotBlank(message = "Fullname boş ola bilməz")

        String fullname,

        @NotBlank(message = "Email boş ola bilməz")
        @Email(message = "Düzgün email formatı daxil edin")
        String email,

        @NotBlank(message = "Password boş ola bilməz")
        String password,

        @NotNull(message = "Rol təyin edilməlidir")
        RoleName roleName

) {}
