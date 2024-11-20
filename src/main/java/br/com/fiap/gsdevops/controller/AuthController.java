package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.Credentials;
import br.com.fiap.gsdevops.model.Token;
import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.service.AuthService;
import br.com.fiap.gsdevops.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        return authService.login(credentials);
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody @Valid Usuario usuario) {
        Usuario createdUsuario = authService.registerUsuario(usuario);
        return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
    }
}
