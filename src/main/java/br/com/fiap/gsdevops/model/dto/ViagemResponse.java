package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Viagem;

import java.time.LocalDate;

public record ViagemResponse(
    Integer idViagem,
    LocalDate dataViagem,
    String localOrigem,
    String localDestino,
    Double distanciaKm,
    Integer idVeiculo
) {
    public ViagemResponse(Viagem viagem) {
        this(
            viagem.getIdViagem(),
            viagem.getDataViagem(),
            viagem.getLocalOrigem(),
            viagem.getLocalDestino(),
            viagem.getDistanciaKm(),
            viagem.getVeiculo().getIdVeiculo()
        );
    }
}
