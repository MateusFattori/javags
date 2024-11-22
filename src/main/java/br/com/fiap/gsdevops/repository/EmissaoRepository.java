package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Emissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmissaoRepository extends JpaRepository<Emissao, Integer> {
    Optional<Emissao> findByViagemIdViagem(Integer idViagem);
}
