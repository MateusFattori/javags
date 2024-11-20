package br.com.fiap.gsdevops.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.gsdevops.model.Credentials;
import br.com.fiap.gsdevops.model.Token;
import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    public Token login(Credentials credentials) {
        Usuario usuario = usuarioRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        if (usuario.getEmail().equals(credentials.email()) && usuario.getSenha().equals(credentials.password())) {
            return tokenService.create(credentials);
        } else {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }
    }

    public Usuario getUserFromToken(String token) {
        return tokenService.getUserFromToken(token); 
    }

        public Usuario createUsuario(Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        usuarioExistente.setSenha(new BCryptPasswordEncoder().encode(usuarioAtualizado.getSenha()));
        
        return usuarioRepository.save(usuarioExistente);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
