package com.motosync.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioRequest(
        @Email(message = "E-mail inv\u00e1lido")
        @NotBlank(message = "E-mail \u00e9 obrigat\u00f3rio")
        String email,

        @NotBlank(message = "CPF \u00e9 obrigat\u00f3rio")
        @Pattern(regexp = "\\\\d{11}", message = "CPF deve conter 11 d\u00edgitos")
        String cpf,

        String telefone,

        @NotBlank(message = "Nome \u00e9 obrigat\u00f3rio")
        String nome,

        String senha,

        Long filialId
) {
}

