package br.com.fiap.gsdevops.model.dto;

import java.time.LocalDate;

public record UsuarioResponse(

    Long idUsuario,

    String nome,

    String email,

    LocalDate dataCadastro
) {}
