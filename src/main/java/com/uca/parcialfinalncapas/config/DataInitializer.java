package com.uca.parcialfinalncapas.config;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (userRepository.findByCorreo("admin@example.com").isEmpty()) {
                User admin = User.builder()
                        .nombre("admin")
                        .correo("admin@example.com")
                        .password(passwordEncoder.encode("admin123"))
                        .nombreRol("TECH")
                        .build();
                userRepository.save(admin);
                System.out.println("Usuario admin creado: admin@example.com / admin123");
            }
        };
    }
} 