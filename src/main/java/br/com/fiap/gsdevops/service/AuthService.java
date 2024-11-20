package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.model.Credentials;
import br.com.fiap.gsdevops.model.Token;
import br.com.fiap.gsdevops.repository.UsuarioRepository;
import br.com.fiap.gsdevops.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(UsuarioRepository usuarioRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(Credentials credentials) {
        Usuario usuario = usuarioRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        if (!passwordEncoder.matches(credentials.password(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }

        return tokenService.create(credentials).token();
    }

    public Usuario validateToken(String token) {
        String email = tokenService.getUserFromToken(token).getEmail();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));
    }

    public Usuario registerUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Usuário já registrado com este email.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public Token login(Credentials credentials) {
        var user = usuarioRepository.findByEmail(credentials.email())
                .orElseThrow( () -> new RuntimeException("Access Denied") );

        if (!passwordEncoder.matches(credentials.password(), user.getSenha()))
            throw  new RuntimeException("Access Denied");

        return tokenService.create(credentials);
    }

}
