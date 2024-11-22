package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Combustivel;

public record CombustivelResponse(Integer idCombustivel, String tipoCombustivel, Double fatorEmissao) {

    public CombustivelResponse(Combustivel combustivel) {
        this(combustivel.getIdCombustivel(), combustivel.getTipoCombustivel(), combustivel.getFatorEmissao());
    }
}
