package br.com.fiap.gsdevops.controller;

import br.com.fiap.gsdevops.model.dto.VeiculoRequest;
import br.com.fiap.gsdevops.model.dto.VeiculoResponse;
import br.com.fiap.gsdevops.service.VeiculoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> createVeiculo(@RequestBody @Valid VeiculoRequest veiculoRequest) {
        logger.info("Recebendo requisição para criar um novo veículo");
        var veiculo = veiculoService.createVeiculo(veiculoRequest);
        logger.info("Veículo criado com sucesso. ID: {}", veiculo.getIdVeiculo());
        return ResponseEntity.ok(new VeiculoResponse(veiculo));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> getAllVeiculos() {
        logger.info("Recebendo requisição para listar todos os veículos");
        var veiculos = veiculoService.getAllVeiculos();
        var response = veiculos.stream().map(VeiculoResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Integer id) {
        logger.info("Recebendo requisição para excluir o veículo com ID: {}", id);
        veiculoService.deleteVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("Erro de execução: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
