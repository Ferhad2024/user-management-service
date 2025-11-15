package org.example.usermanagmentservice.Service.Service;
import org.example.usermanagmentservice.request.UserRequest;
import org.example.usermanagmentservice.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse>getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse createUser(UserRequest userRequest);
    void deleteUserById(Long id);
}
