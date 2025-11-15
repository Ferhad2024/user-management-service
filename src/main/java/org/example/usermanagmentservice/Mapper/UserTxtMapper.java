package org.example.usermanagmentservice.Mapper;


import org.example.usermanagmentservice.Enum.TextStatus;
import org.example.usermanagmentservice.entity.User;
import org.example.usermanagmentservice.entity.UserTxt;
import org.example.usermanagmentservice.request.UserTxtRequest;
import org.example.usermanagmentservice.response.UserTxtResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class UserTxtMapper {

    public UserTxt toEntity(UserTxtRequest request, User user) throws IOException {
        MultipartFile file = request.textfile();
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);

        return UserTxt.builder()
                .user(user)
                .fileContent(content)
                .status(TextStatus.PENDING)
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();
    }

    public UserTxtResponse toResponse(UserTxt entity) {
        return new UserTxtResponse(
                entity.getId(),
                entity.getFileName(),
                entity.getContentType(),
                entity.getCreatedAt(),
                entity.getFileContent()
        );
    }

}