package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
    List<Veiculo> findByUsuario_IdUsuario(Integer idUsuario);
    List<Veiculo> findByCombustivel_IdCombustivel(Integer idCombustivel);

}
