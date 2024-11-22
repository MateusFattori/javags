package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Emissao;

public record EmissaoResponse(
    Integer idEmissao,
    Integer idViagem,
    Double litrosConsumidos,
    Double emissaoCo2Kg
) {
    public EmissaoResponse(Emissao emissao) {
        this(emissao.getIdEmissao(), emissao.getViagem().getIdViagem(), emissao.getLitrosConsumidos(), emissao.getEmissaoCo2Kg());
    }
}
