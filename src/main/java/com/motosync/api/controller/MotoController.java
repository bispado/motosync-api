package com.motosync.api.controller;

import com.motosync.api.dto.MotoRequest;
import com.motosync.api.dto.MotoResponse;
import com.motosync.api.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public List<MotoResponse> listar() {
        return motoService.listar();
    }

    @GetMapping("/{id}")
    public MotoResponse buscar(@PathVariable Long id) {
        return motoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<MotoResponse> criar(@Valid @RequestBody MotoRequest request) {
        MotoResponse response = motoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public MotoResponse atualizar(@PathVariable Long id, @Valid @RequestBody MotoRequest request) {
        return motoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        motoService.remover(id);
    }
}

