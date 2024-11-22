package br.com.fiap.gsdevops.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "{auth.email.blank}")
    @Email(message = "{usuario.email.email}")
    String email,

    @NotBlank(message = "{auth.senha.blank}")
    String senha
) {}
