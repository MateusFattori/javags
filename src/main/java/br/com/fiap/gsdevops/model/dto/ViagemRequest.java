package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Veiculo;
import br.com.fiap.gsdevops.model.Viagem;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViagemRequest(
    @NotNull LocalDate dataViagem,
    @NotNull String localOrigem,
    @NotNull String localDestino,
    @NotNull Double distanciaKm,
    @NotNull Integer idVeiculo
) {
    public Viagem toModel(Veiculo veiculo) {
        return new Viagem(null, dataViagem, localOrigem, localDestino, distanciaKm, veiculo);
    }
}
