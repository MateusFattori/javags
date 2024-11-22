package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
    List<Veiculo> findByUsuario(Usuario usuario);
    void deleteByUsuario(Usuario usuario);
}
