package com.motosync.api.service;

import com.motosync.api.dto.MotoRequest;
import com.motosync.api.dto.MotoResponse;
import com.motosync.api.model.Filial;
import com.motosync.api.model.Moto;
import com.motosync.api.model.Usuario;
import com.motosync.api.repository.FilialRepository;
import com.motosync.api.repository.MotoRepository;
import com.motosync.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class MotoService {

    private final MotoRepository motoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FilialRepository filialRepository;

    public MotoService(MotoRepository motoRepository,
                       UsuarioRepository usuarioRepository,
                       FilialRepository filialRepository) {
        this.motoRepository = motoRepository;
        this.usuarioRepository = usuarioRepository;
        this.filialRepository = filialRepository;
    }

    public List<MotoResponse> listar() {
        return motoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MotoResponse buscarPorId(Long id) {
        return motoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Moto n\u00e3o encontrada: " + id));
    }

    public MotoResponse criar(MotoRequest request) {
        validarDuplicidade(request, null);

        Moto moto = new Moto();
        moto.setModelo(request.modelo());
        moto.setPlaca(request.placa());
        moto.setChassi(request.chassi());
        moto.setResponsavel(resolverUsuario(request.responsavelId()));
        moto.setFilial(resolverFilial(request.filialId()));
        moto.setIotInfo(request.iotInfo());
        moto.setRfidTag(request.rfidTag());
        moto.setLocalizacao(request.localizacao());
        moto.setStatusAtual(request.statusAtual());

        Moto salva = motoRepository.save(moto);
        return toResponse(salva);
    }

    public MotoResponse atualizar(Long id, MotoRequest request) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto n\u00e3o encontrada: " + id));

        validarDuplicidade(request, moto);

        moto.setModelo(request.modelo());
        moto.setPlaca(request.placa());
        moto.setChassi(request.chassi());
        moto.setResponsavel(resolverUsuario(request.responsavelId()));
        moto.setFilial(resolverFilial(request.filialId()));
        moto.setIotInfo(request.iotInfo());
        moto.setRfidTag(request.rfidTag());
        moto.setLocalizacao(request.localizacao());
        moto.setStatusAtual(request.statusAtual());

        return toResponse(moto);
    }

    public void remover(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new IllegalArgumentException("Moto n\u00e3o encontrada: " + id);
        }
        motoRepository.deleteById(id);
    }

    private void validarDuplicidade(MotoRequest request, Moto atual) {
        motoRepository.findByPlaca(request.placa())
                .filter(moto -> !Objects.equals(moto.getId(), atual != null ? atual.getId() : null))
                .ifPresent(moto -> {
                    throw new IllegalArgumentException("Placa j\u00e1 cadastrada: " + request.placa());
                });

        motoRepository.findByChassi(request.chassi())
                .filter(moto -> !Objects.equals(moto.getId(), atual != null ? atual.getId() : null))
                .ifPresent(moto -> {
                    throw new IllegalArgumentException("Chassi j\u00e1 cadastrado: " + request.chassi());
                });
    }

    private Usuario resolverUsuario(Long usuarioId) {
        if (usuarioId == null) {
            return null;
        }
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usu\u00e1rio n\u00e3o encontrado: " + usuarioId));
    }

    private Filial resolverFilial(Long filialId) {
        if (filialId == null) {
            return null;
        }
        return filialRepository.findById(filialId)
                .orElseThrow(() -> new IllegalArgumentException("Filial n\u00e3o encontrada: " + filialId));
    }

    private MotoResponse toResponse(Moto moto) {
        return new MotoResponse(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                moto.getChassi(),
                moto.getResponsavel() != null ? moto.getResponsavel().getId() : null,
                moto.getResponsavel() != null ? moto.getResponsavel().getNome() : null,
                moto.getFilial() != null ? moto.getFilial().getId() : null,
                moto.getFilial() != null ? moto.getFilial().getNome() : null,
                moto.getIotInfo(),
                moto.getRfidTag(),
                moto.getLocalizacao(),
                moto.getStatusAtual()
        );
    }
}

