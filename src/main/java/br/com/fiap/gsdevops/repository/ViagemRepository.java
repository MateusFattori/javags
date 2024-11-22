package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Integer> {
    List<Viagem> findByVeiculo_IdVeiculo(Integer idVeiculo);
    List<Viagem> findByLocalOrigemContainingIgnoreCaseOrLocalDestinoContainingIgnoreCase(String origem, String destino);
}
