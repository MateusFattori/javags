package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Combustivel;
import br.com.fiap.gsdevops.repository.CombustivelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CombustivelService {

    private final CombustivelRepository combustivelRepository;

    public CombustivelService(CombustivelRepository combustivelRepository) {
        this.combustivelRepository = combustivelRepository;
    }

    public Combustivel save(Combustivel combustivel) {
        return combustivelRepository.save(combustivel);
    }

    public List<Combustivel> getAllCombustiveis() {
        return combustivelRepository.findAll();
    }

    public void deleteCombustivel(Integer id) {
        if (!combustivelRepository.existsById(id)) {
            throw new RuntimeException("Combustível não encontrado");
        }
        combustivelRepository.deleteById(id);
    }
}
