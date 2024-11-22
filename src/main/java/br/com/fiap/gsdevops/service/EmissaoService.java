package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Emissao;
import br.com.fiap.gsdevops.model.Viagem;
import br.com.fiap.gsdevops.model.dto.EmissaoRequest;
import br.com.fiap.gsdevops.repository.EmissaoRepository;
import br.com.fiap.gsdevops.repository.ViagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmissaoService {

    private final EmissaoRepository emissaoRepository;
    private final ViagemRepository viagemRepository;

    public EmissaoService(EmissaoRepository emissaoRepository, ViagemRepository viagemRepository) {
        this.emissaoRepository = emissaoRepository;
        this.viagemRepository = viagemRepository;
    }

    public Emissao createEmissao(EmissaoRequest emissaoRequest) {
        Viagem viagem = viagemRepository.findById(emissaoRequest.idViagem())
            .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));
        Emissao emissao = emissaoRequest.toModel(viagem);
        return emissaoRepository.save(emissao);
    }
    

    public List<Emissao> getAllEmissoes() {
        return emissaoRepository.findAll();
    }

    public void deleteEmissao(Integer id) {
        if (!emissaoRepository.existsById(id)) {
            throw new RuntimeException("Emissão não encontrada");
        }
        emissaoRepository.deleteById(id);
    }
}
