package org.example.usermanagmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.example.usermanagmentservice.entity")
@EnableJpaRepositories(basePackages = "org.example.usermanagmentservice.repository")
public class UsermanagmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsermanagmentServiceApplication.class, args);
    }

}
