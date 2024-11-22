package br.com.fiap.gsdevops.model.dto;

import br.com.fiap.gsdevops.model.Combustivel;

public record CombustivelRequest(String tipoCombustivel, Double fatorEmissao) {
    
    public Combustivel toModel() {
        return new Combustivel(null, tipoCombustivel, fatorEmissao);
    }
}
