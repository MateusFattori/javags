package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.dto.EmissaoRequest;
import br.com.fiap.gsdevops.model.dto.EmissaoResponse;
import br.com.fiap.gsdevops.service.EmissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emissoes")
public class EmissaoController {

    private final EmissaoService emissaoService;

    public EmissaoController(EmissaoService emissaoService) {
        this.emissaoService = emissaoService;
    }

    @PostMapping
    public ResponseEntity<EmissaoResponse> createEmissao(@RequestBody EmissaoRequest emissaoRequest) {
        var emissao = emissaoService.createEmissao(emissaoRequest);
        return ResponseEntity.ok(new EmissaoResponse(emissao));
    }

    @GetMapping
    public ResponseEntity<List<EmissaoResponse>> getAllEmissoes() {
        var emissoes = emissaoService.getAllEmissoes();
        var response = emissoes.stream().map(EmissaoResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmissao(@PathVariable Integer id) {
        emissaoService.deleteEmissao(id);
        return ResponseEntity.noContent().build();
    }
}
