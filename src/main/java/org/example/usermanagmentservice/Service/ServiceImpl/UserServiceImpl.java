package org.example.usermanagmentservice.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.usermanagmentservice.entity.User;
import org.example.usermanagmentservice.Exception.UserAlreadyExistsException;
import org.example.usermanagmentservice.Exception.UserNotFoundException;
import org.example.usermanagmentservice.Mapper.UserResponseRequestMapper;
import org.example.usermanagmentservice.Service.Service.UserService;
import org.example.usermanagmentservice.repository.UserRepository;
import org.example.usermanagmentservice.request.UserRequest;
import org.example.usermanagmentservice.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserResponseRequestMapper mapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll()
                .stream()
                .filter(User::isActive)
                .toList();
        if (users.isEmpty()) {
            throw new UserNotFoundException("User list is empty.");
        }
        return users.stream()
                .map(mapper::convertUserResponse)
                .toList();
    }
    @Override
    public UserResponse getUserById(Long id) {
        var user = getActiveUserOrThrow(id);
        return mapper.convertUserResponse(user);
    }
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new UserAlreadyExistsException("Bu username artıq istifadə olunur.");
        }
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new UserAlreadyExistsException("Bu email artıq qeydiyyatdan keçib.");
        }
        var user = mapper.convertUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        userRepository.save(user);
        log.info("Yeni istifadəçi yaradıldı: {}", user.getUsername());
        return mapper.convertUserResponse(user);
    }
    @Override
    public void deleteUserById(Long id) {
        var user = getActiveUserOrThrow(id);

        if (user.getText() != null && !user.getText().isEmpty()) {
            user.getText().clear();
            log.warn("UserTxt faylları silindi: {}", user.getId());
        }
        user.setActive(false);
        userRepository.save(user);
        log.warn("İstifadəçi (ID={}) deaktiv edildi.", id);
    }
    private User getActiveUserOrThrow(Long id) {
        return userRepository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() ->
                        new UserNotFoundException("ID " + id + " olan istifadəçi tapılmadı və ya deaktivdir."));
    }
}
