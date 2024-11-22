package br.com.fiap.gsdevops.repository;

import br.com.fiap.gsdevops.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
    
    List<Usuario> findByDataCadastroAfter(LocalDateTime date);


    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    boolean existsByEmailAndIdUsuarioNot(String email, Integer idUsuario);
}
