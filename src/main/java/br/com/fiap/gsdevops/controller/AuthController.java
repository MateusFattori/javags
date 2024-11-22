package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.Credentials;
import br.com.fiap.gsdevops.service.AuthService;
import groovy.util.logging.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials) {
        try {
            authService.authenticate(credentials);
            return ResponseEntity.ok("Usuário autenticado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}
