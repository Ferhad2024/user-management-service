package org.example.usermanagmentservice.config;

import org.example.usermanagmentservice.Service.ServiceImpl.UserDetailsServiceImpl;
import org.example.usermanagmentservice.Service.securityService.JwtAuthenticationFilter;
import org.example.usermanagmentservice.Service.securityService.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService tokenService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsCfg = new CorsConfiguration();
                    corsCfg.setAllowedOrigins(List.of(
                            "http://localhost:5500",
                            "http://127.0.0.1:5500"
                    ));
                    corsCfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                    corsCfg.setAllowedHeaders(List.of("*"));
                    corsCfg.setAllowCredentials(true); // refresh token cookie üçün vacibdir
                    return corsCfg;
                }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // public endpoints
                        .requestMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/", "/index.html", "/login.html", "/register.html").permitAll()
                        .requestMatchers("/app/auth/**").permitAll()

                        // authenticated users
                        .requestMatchers("/app/dashboard/images/**").authenticated()

                        // ADMIN only
                        .requestMatchers("/app/dashboard/**").hasRole("ADMIN")

                        // bütün digər endpoint-lər
                        .anyRequest().authenticated()
                )
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
