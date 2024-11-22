package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);


    boolean existsByEmailAndIdUsuarioNot(String email, Integer idUsuario);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
