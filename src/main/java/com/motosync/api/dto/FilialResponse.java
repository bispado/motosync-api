package com.motosync.api.dto;

public record FilialResponse(
        Long id,
        String nome,
        String endereco,
        String contato,
        String horarioFuncionamento
) {
}

