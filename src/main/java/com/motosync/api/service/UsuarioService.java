package com.motosync.api.service;

import com.motosync.api.dto.UsuarioRequest;
import com.motosync.api.dto.UsuarioResponse;
import com.motosync.api.model.Filial;
import com.motosync.api.model.Usuario;
import com.motosync.api.repository.FilialRepository;
import com.motosync.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FilialRepository filialRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, FilialRepository filialRepository) {
        this.usuarioRepository = usuarioRepository;
        this.filialRepository = filialRepository;
    }

    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Usu\u00e1rio n\u00e3o encontrado: " + id));
    }

    public UsuarioResponse criar(UsuarioRequest request) {
        validarDuplicidade(request, null);

        String senha = prepararSenha(request.senha(), true);

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        usuario.setCpf(request.cpf());
        usuario.setTelefone(request.telefone());
        usuario.setNome(request.nome());
        usuario.setSenha(senha);
        usuario.setFilial(resolverFilial(request.filialId()));

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo);
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usu\u00e1rio n\u00e3o encontrado: " + id));

        validarDuplicidade(request, usuario);

        usuario.setEmail(request.email());
        usuario.setCpf(request.cpf());
        usuario.setTelefone(request.telefone());
        usuario.setNome(request.nome());
        String novaSenha = prepararSenha(request.senha(), false);
        if (novaSenha != null) {
            usuario.setSenha(novaSenha);
        }
        usuario.setFilial(resolverFilial(request.filialId()));

        return toResponse(usuario);
    }

    public void remover(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usu\u00e1rio n\u00e3o encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void validarDuplicidade(UsuarioRequest request, Usuario atual) {
        usuarioRepository.findByEmail(request.email())
                .filter(usuario -> !Objects.equals(usuario.getId(), atual != null ? atual.getId() : null))
                .ifPresent(usuario -> {
                    throw new IllegalArgumentException("E-mail já cadastrado: " + request.email());
                });

        usuarioRepository.findByCpf(request.cpf())
                .filter(usuario -> !Objects.equals(usuario.getId(), atual != null ? atual.getId() : null))
                .ifPresent(usuario -> {
                    throw new IllegalArgumentException("CPF já cadastrado: " + request.cpf());
                });
    }

    private String prepararSenha(String senha, boolean obrigatoria) {
        if (senha == null || senha.isBlank()) {
            if (obrigatoria) {
                throw new IllegalArgumentException("Senha é obrigatória e deve conter ao menos 6 caracteres");
            }
            return null;
        }

        String sanitizada = senha.trim();
        if (sanitizada.length() < 6) {
            throw new IllegalArgumentException("Senha deve conter ao menos 6 caracteres");
        }
        return sanitizada;
    }

    private Filial resolverFilial(Long filialId) {
        if (filialId == null) {
            return null;
        }
        return filialRepository.findById(filialId)
                .orElseThrow(() -> new IllegalArgumentException("Filial n\u00e3o encontrada: " + filialId));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getNome(),
                usuario.getFilial() != null ? usuario.getFilial().getId() : null,
                usuario.getFilial() != null ? usuario.getFilial().getNome() : null
        );
    }
}

