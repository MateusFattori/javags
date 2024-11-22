package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.model.dto.UsuarioResponse;
import br.com.fiap.gsdevops.model.dto.UsuarioRequest;
import br.com.fiap.gsdevops.service.TokenService;
import br.com.fiap.gsdevops.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario(
                null,
                usuarioRequest.nome(),
                usuarioRequest.email(),
                usuarioRequest.senha(),
                null
        );

        Usuario createdUsuario = usuarioService.createUsuario(usuario);

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                createdUsuario.getIdUsuario(),
                createdUsuario.getNome(),
                createdUsuario.getEmail(),
                createdUsuario.getDataCadastro()
        );

        return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> updateUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest,
                                                         @RequestHeader("Authorization") String token) {
        Usuario usuario = tokenService.getUserFromToken(token);

        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setSenha(usuarioRequest.senha());

        usuarioService.updateUsuario(usuario.getIdUsuario(), usuario);

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro()
        );

        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.getAllUsuarios()
                .stream()
                .map(usuario -> new UsuarioResponse(
                        usuario.getIdUsuario(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getDataCadastro()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsuarioResponse>> searchByNome(@RequestParam String nome) {
        List<UsuarioResponse> usuarios = usuarioService.searchByNome(nome)
                .stream()
                .map(usuario -> new UsuarioResponse(
                        usuario.getIdUsuario(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getDataCadastro()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }
}
