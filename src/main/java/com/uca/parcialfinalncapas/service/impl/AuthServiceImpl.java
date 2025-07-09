package com.uca.parcialfinalncapas.service.impl;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.request.RefreshTokenRequest;
import com.uca.parcialfinalncapas.dto.response.AuthResponse;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.exceptions.UserNotFoundException;
import com.uca.parcialfinalncapas.repository.UserRepository;
import com.uca.parcialfinalncapas.service.AuthService;
import com.uca.parcialfinalncapas.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreo(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        User user = userRepository.findByCorreo(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .correo(user.getCorreo())
                .nombreRol(user.getNombreRol())
                .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        String userEmail = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByCorreo(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        org.springframework.security.core.userdetails.UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getCorreo())
                .password(user.getPassword())
                .authorities(user.getNombreRol())
                .build();
        
        String newAccessToken = jwtUtil.generateAccessToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .correo(user.getCorreo())
                .nombreRol(user.getNombreRol())
                .build();
    }
} 