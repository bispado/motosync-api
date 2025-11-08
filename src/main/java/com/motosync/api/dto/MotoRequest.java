package com.motosync.api.dto;

import jakarta.validation.constraints.NotBlank;

public record MotoRequest(
        @NotBlank(message = "Modelo \u00e9 obrigat\u00f3rio")
        String modelo,

        @NotBlank(message = "Placa \u00e9 obrigat\u00f3ria")
        String placa,

        @NotBlank(message = "Chassi \u00e9 obrigat\u00f3rio")
        String chassi,

        Long responsavelId,
        Long filialId,
        String iotInfo,
        String rfidTag,
        String localizacao,
        String statusAtual
) {
}

