package com.motosync.api.dto;

import jakarta.validation.constraints.NotBlank;

public record FilialRequest(
        @NotBlank(message = "Nome \u00e9 obrigat\u00f3rio")
        String nome,

        @NotBlank(message = "Endere\u00e7o \u00e9 obrigat\u00f3rio")
        String endereco,

        String contato,
        String horarioFuncionamento
) {
}

