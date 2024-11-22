package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.dto.ViagemRequest;
import br.com.fiap.gsdevops.model.dto.ViagemResponse;
import br.com.fiap.gsdevops.service.ViagemService;
import groovy.util.logging.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/viagens")
@Validated
@Slf4j
public class ViagemController {

    private final ViagemService viagemService;

    public ViagemController(ViagemService viagemService) {
        this.viagemService = viagemService;
    }

    @PostMapping
    public ResponseEntity<ViagemResponse> createViagem(@RequestBody ViagemRequest viagemRequest) {
        var viagem = viagemService.createViagem(viagemRequest);
        return ResponseEntity.ok(new ViagemResponse(viagem));
    }

    @GetMapping
    public ResponseEntity<List<ViagemResponse>> getAllViagens() {
        var viagens = viagemService.getAllViagens();
        var response = viagens.stream().map(ViagemResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViagem(@PathVariable Integer id) {
        viagemService.deleteViagem(id);
        return ResponseEntity.noContent().build();
    }
}
