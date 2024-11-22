package br.com.fiap.gsdevops.model.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
    Integer idUsuario,
    String nome,
    String email,
    String senha,
    LocalDateTime dataCadastro
) {}
