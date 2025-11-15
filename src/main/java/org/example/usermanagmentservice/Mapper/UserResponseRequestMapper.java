package org.example.usermanagmentservice.Mapper;

import org.example.usermanagmentservice.entity.User;
import org.example.usermanagmentservice.request.UserRequest;
import org.example.usermanagmentservice.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserResponseRequestMapper {
    private  PasswordEncoder passwordEncoder;

    public UserResponseRequestMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public User convertUser(UserRequest request){
        return User.builder()
                .email(request.email())
                .fullname(request.fullname())
                .password(passwordEncoder.encode(request.password())) // Şifrələnmə buradadır
                .username(request.username())
                .roleName(request.roleName())
                .build();
    }
    public UserResponse convertUserResponse(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getFullname(), user.getEmail(), user.getPassword());
    }
}