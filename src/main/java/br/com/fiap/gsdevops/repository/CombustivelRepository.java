package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Combustivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CombustivelRepository extends JpaRepository<Combustivel, Integer> {
    Optional<Combustivel> findByTipoCombustivel(String tipoCombustivel);
}
