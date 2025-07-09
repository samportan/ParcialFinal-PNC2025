package com.uca.parcialfinalncapas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
} 