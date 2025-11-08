package com.motosync.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private Long id;

    @Column(nullable = false, length = 255)
    private String modelo;

    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Column(nullable = false, unique = true, length = 50)
    private String chassi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Usuario responsavel;

    @Column(name = "iot_info", length = 255)
    private String iotInfo;

    @Column(name = "rfid_tag", length = 255)
    private String rfidTag;

    @Column(name = "localizacao", length = 255)
    private String localizacao;

    @Column(name = "status_atual", columnDefinition = "CLOB")
    private String statusAtual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filial")
    private Filial filial;

    public Moto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public String getIotInfo() {
        return iotInfo;
    }

    public void setIotInfo(String iotInfo) {
        this.iotInfo = iotInfo;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
}

