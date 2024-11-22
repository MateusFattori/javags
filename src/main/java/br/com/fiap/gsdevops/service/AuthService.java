package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Credentials;
import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario authenticate(Credentials credentials) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado ou email inválido"));

        // Verifica se a senha fornecida corresponde à senha armazenada
        if (!passwordEncoder.matches(credentials.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }

        return usuario;
    }
}
