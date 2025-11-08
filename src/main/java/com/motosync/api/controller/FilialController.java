package com.motosync.api.controller;

import com.motosync.api.dto.FilialRequest;
import com.motosync.api.dto.FilialResponse;
import com.motosync.api.service.FilialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filiais")
public class FilialController {

    private final FilialService filialService;

    public FilialController(FilialService filialService) {
        this.filialService = filialService;
    }

    @GetMapping
    public List<FilialResponse> listar() {
        return filialService.listar();
    }

    @GetMapping("/{id}")
    public FilialResponse buscar(@PathVariable Long id) {
        return filialService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<FilialResponse> criar(@Valid @RequestBody FilialRequest request) {
        FilialResponse response = filialService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public FilialResponse atualizar(@PathVariable Long id, @Valid @RequestBody FilialRequest request) {
        return filialService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        filialService.remover(id);
    }
}

