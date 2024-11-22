package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Combustivel;
import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.model.Veiculo;
import jakarta.validation.constraints.NotNull;

public record VeiculoRequest(
    @NotNull String marca,
    @NotNull String modelo,
    @NotNull Integer anoFabricacao,
    @NotNull Double consumoPorKm,
    @NotNull Integer idUsuario,
    @NotNull Integer idCombustivel
) {
    public Veiculo toModel(Usuario usuario, Combustivel combustivel) {
        return new Veiculo(null, marca, modelo, anoFabricacao, consumoPorKm, usuario, combustivel);
    }
}
