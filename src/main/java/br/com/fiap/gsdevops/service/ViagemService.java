package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Viagem;
import br.com.fiap.gsdevops.model.dto.ViagemRequest;
import br.com.fiap.gsdevops.repository.VeiculoRepository;
import br.com.fiap.gsdevops.repository.ViagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final VeiculoRepository veiculoRepository;

    public ViagemService(ViagemRepository viagemRepository, VeiculoRepository veiculoRepository) {
        this.viagemRepository = viagemRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Viagem createViagem(ViagemRequest viagemRequest) {
        var veiculo = veiculoRepository.findById(viagemRequest.idVeiculo())
            .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        Viagem viagem = viagemRequest.toModel(veiculo);
        return viagemRepository.save(viagem);
    }

    public List<Viagem> getAllViagens() {
        return viagemRepository.findAll();
    }

    public void deleteViagem(Integer id) {
        if (!viagemRepository.existsById(id)) {
            throw new RuntimeException("Viagem não encontrada");
        }
        viagemRepository.deleteById(id);
    }
}
