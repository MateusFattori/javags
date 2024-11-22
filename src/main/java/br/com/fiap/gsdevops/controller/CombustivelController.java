package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.dto.CombustivelRequest;
import br.com.fiap.gsdevops.model.dto.CombustivelResponse;
import br.com.fiap.gsdevops.service.CombustivelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/combustiveis")
public class CombustivelController {

    private final CombustivelService combustivelService;

    public CombustivelController(CombustivelService combustivelService) {
        this.combustivelService = combustivelService;
    }

    @PostMapping
    public ResponseEntity<CombustivelResponse> createCombustivel(@RequestBody CombustivelRequest combustivelRequest) {
        var combustivel = combustivelRequest.toModel();
        var savedCombustivel = combustivelService.save(combustivel);
        return ResponseEntity.ok(new CombustivelResponse(savedCombustivel));
    }

    @GetMapping
    public ResponseEntity<List<CombustivelResponse>> getAllCombustiveis() {
        var combustiveis = combustivelService.getAllCombustiveis();
        var response = combustiveis.stream().map(CombustivelResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombustivel(@PathVariable Integer id) {
        combustivelService.deleteCombustivel(id);
        return ResponseEntity.noContent().build();
    }
}
