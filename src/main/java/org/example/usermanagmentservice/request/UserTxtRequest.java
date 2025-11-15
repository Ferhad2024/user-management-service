package org.example.usermanagmentservice.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UserTxtRequest(

        @NotNull(message = "Fayl göndərilməlidir")
        MultipartFile textfile

) {}
