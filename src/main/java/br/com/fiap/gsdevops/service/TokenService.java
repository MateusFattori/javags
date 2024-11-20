package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Token;
import br.com.fiap.gsdevops.model.Credentials;
import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.repository.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final UsuarioRepository usuarioRepository;
    private Algorithm algorithm;

    public TokenService(UsuarioRepository usuarioRepository, @Value("${jwt.secret}") String secret) {
        this.usuarioRepository = usuarioRepository;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public Token create(Credentials credentials) {
        Usuario usuario = usuarioRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        var expiresAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));
        var token = JWT.create()
                .withSubject(usuario.getEmail())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new Token(token, usuario.getEmail());
    }

    public Usuario getUserFromToken(String token) {
        var email = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}
