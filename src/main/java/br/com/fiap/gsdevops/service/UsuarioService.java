package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TaskSender taskSender;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TaskSender taskSender) {
        this.usuarioRepository = usuarioRepository;
        this.taskSender = taskSender;
    }

    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        String assunto = "Confirmação de Cadastro";
        String mensagem = String.format("Olá %s, seu cadastro foi realizado com sucesso!", usuarioSalvo.getNome());
        taskSender.sendEmailTask(usuarioSalvo.getEmail(), assunto, mensagem);

        return usuarioSalvo;
    }

    public Usuario updateUsuario(Integer id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuarioRepository.existsByEmailAndIdUsuarioNot(usuarioAtualizado.getEmail(), id)) {
            throw new RuntimeException("E-mail já cadastrado para outro usuário.");
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setSenha(new BCryptPasswordEncoder().encode(usuarioAtualizado.getSenha()));

        return usuarioRepository.save(usuarioExistente);
    }

    public void deleteUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> searchByNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}
