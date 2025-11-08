package com.motosync.api.dto;

public record UsuarioResponse(
        Long id,
        String email,
        String cpf,
        String telefone,
        String nome,
        Long filialId,
        String filialNome
) {
}

