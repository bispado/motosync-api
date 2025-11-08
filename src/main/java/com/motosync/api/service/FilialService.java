package com.motosync.api.service;

import com.motosync.api.dto.FilialRequest;
import com.motosync.api.dto.FilialResponse;
import com.motosync.api.model.Filial;
import com.motosync.api.repository.FilialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FilialService {

    private final FilialRepository filialRepository;

    public FilialService(FilialRepository filialRepository) {
        this.filialRepository = filialRepository;
    }

    public List<FilialResponse> listar() {
        return filialRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public FilialResponse buscarPorId(Long id) {
        return filialRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Filial n\u00e3o encontrada: " + id));
    }

    public FilialResponse criar(FilialRequest request) {
        Filial filial = new Filial();
        filial.setNome(request.nome());
        filial.setEndereco(request.endereco());
        filial.setContato(request.contato());
        filial.setHorarioFuncionamento(request.horarioFuncionamento());

        Filial salva = filialRepository.save(filial);
        return toResponse(salva);
    }

    public FilialResponse atualizar(Long id, FilialRequest request) {
        Filial filial = filialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filial n\u00e3o encontrada: " + id));

        filial.setNome(request.nome());
        filial.setEndereco(request.endereco());
        filial.setContato(request.contato());
        filial.setHorarioFuncionamento(request.horarioFuncionamento());

        return toResponse(filial);
    }

    public void remover(Long id) {
        if (!filialRepository.existsById(id)) {
            throw new IllegalArgumentException("Filial n\u00e3o encontrada: " + id);
        }
        filialRepository.deleteById(id);
    }

    private FilialResponse toResponse(Filial filial) {
        return new FilialResponse(
                filial.getId(),
                filial.getNome(),
                filial.getEndereco(),
                filial.getContato(),
                filial.getHorarioFuncionamento()
        );
    }
}

