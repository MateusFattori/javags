package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Emissao;
import br.com.fiap.gsdevops.model.Viagem;
import jakarta.validation.constraints.NotNull;

public record EmissaoRequest(
    @NotNull Integer idViagem,
    @NotNull Double litrosConsumidos,
    @NotNull Double emissaoCo2Kg
) {
    public Emissao toModel(Viagem viagem) {
        return new Emissao(null, viagem, litrosConsumidos, emissaoCo2Kg);
    }
}
