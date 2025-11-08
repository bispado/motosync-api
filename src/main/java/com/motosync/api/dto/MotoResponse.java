package com.motosync.api.dto;

public record MotoResponse(
        Long id,
        String modelo,
        String placa,
        String chassi,
        Long responsavelId,
        String responsavelNome,
        Long filialId,
        String filialNome,
        String iotInfo,
        String rfidTag,
        String localizacao,
        String statusAtual
) {
}

