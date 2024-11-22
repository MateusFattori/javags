package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Veiculo;
import br.com.fiap.gsdevops.model.dto.VeiculoRequest;
import br.com.fiap.gsdevops.repository.CombustivelRepository;
import br.com.fiap.gsdevops.repository.UsuarioRepository;
import br.com.fiap.gsdevops.repository.VeiculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoService.class);

    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CombustivelRepository combustivelRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, UsuarioRepository usuarioRepository, CombustivelRepository combustivelRepository) {
        this.veiculoRepository = veiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.combustivelRepository = combustivelRepository;
    }

    public Veiculo createVeiculo(VeiculoRequest veiculoRequest) {
        logger.info("Criando um veículo para o usuário ID: {}", veiculoRequest.idUsuario());

        var usuario = usuarioRepository.findById(veiculoRequest.idUsuario())
            .orElseThrow(() -> {
                logger.error("Usuário com ID {} não encontrado", veiculoRequest.idUsuario());
                return new RuntimeException("Usuário não encontrado");
            });

        var combustivel = combustivelRepository.findById(veiculoRequest.idCombustivel())
            .orElseThrow(() -> {
                logger.error("Combustível com ID {} não encontrado", veiculoRequest.idCombustivel());
                return new RuntimeException("Combustível não encontrado");
            });

        Veiculo veiculo = veiculoRequest.toModel(usuario, combustivel);
        Veiculo veiculoCriado = veiculoRepository.save(veiculo);

        logger.info("Veículo criado com sucesso. ID: {}", veiculoCriado.getIdVeiculo());
        return veiculoCriado;
    }

    public List<Veiculo> getAllVeiculos() {
        logger.info("Buscando todos os veículos");
        return veiculoRepository.findAll();
    }

    public void deleteVeiculo(Integer id) {
        logger.info("Tentando excluir o veículo com ID: {}", id);

        if (!veiculoRepository.existsById(id)) {
            logger.error("Veículo com ID {} não encontrado", id);
            throw new RuntimeException("Veículo não encontrado");
        }

        veiculoRepository.deleteById(id);
        logger.info("Veículo com ID {} excluído com sucesso", id);
    }
}
