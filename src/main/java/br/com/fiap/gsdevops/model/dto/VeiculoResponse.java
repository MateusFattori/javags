package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Veiculo;

public record VeiculoResponse(
    Integer idVeiculo,
    String marca,
    String modelo,
    Integer anoFabricacao,
    Double consumoPorKm,
    Integer idUsuario,
    Integer idCombustivel
) {
    public VeiculoResponse(Veiculo veiculo) {
        this(
            veiculo.getIdVeiculo(),
            veiculo.getMarca(),
            veiculo.getModelo(),
            veiculo.getAnoFabricacao(),
            veiculo.getConsumoPorKm(),
            veiculo.getUsuario().getIdUsuario(),
            veiculo.getCombustivel().getIdCombustivel()
        );
    }
}
