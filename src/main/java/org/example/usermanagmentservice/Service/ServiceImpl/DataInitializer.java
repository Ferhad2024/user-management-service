package org.example.usermanagmentservice.Service.ServiceImpl;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.entity.User;
import org.example.usermanagmentservice.Enum.RoleName;
import org.example.usermanagmentservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // final olaraq inject edirik

    @PostConstruct
    public void init() {
        // Əgər admin artıq varsa, əlavə etməyək
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .fullname("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123")) // artıq null deyil
                    .roleName(RoleName.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
        }
    }
}
