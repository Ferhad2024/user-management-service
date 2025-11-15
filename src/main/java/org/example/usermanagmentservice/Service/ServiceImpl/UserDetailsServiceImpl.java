package org.example.usermanagmentservice.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.Exception.UserNotFoundException;
import org.example.usermanagmentservice.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("Not Found Username"+username));
     return User.builder()
             .authorities(List.of(new SimpleGrantedAuthority("ROLE_"+user.getRoleName().name())))
             .password(user.getPassword())
             .username(user.getUsername())
             .build();

    }
}
