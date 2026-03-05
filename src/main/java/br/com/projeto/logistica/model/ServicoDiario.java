package br.com.projeto.logistica.model;

import java.time.LocalDateTime;

public class ServicoDiario {
    private String cliente;
    private String nomeMotorista;
    private String placaCavalo;
    private String placaCarreta; // pode ser null se for truck
    private int notaFiscal;
    private TipoOperacao tipoOperacao; // "ENTRADA", "SAIDA" ou "RETRABALHO"
    private LocalDateTime dataHoraRegistro;
    private String operadorRegistro;
    private StatusServico status;


    public ServicoDiario(String cliente, String nomeMotorista, String placaCavalo,
                         String placaCarreta, int notaFiscal, TipoOperacao tipoOperacao,
                         String operadorRegistro) {
        this.cliente = cliente;
        this.nomeMotorista = nomeMotorista;
        this.placaCavalo = placaCavalo;
        this.placaCarreta = placaCarreta;
        this.notaFiscal = notaFiscal;
        this.tipoOperacao = tipoOperacao;
        this.operadorRegistro = operadorRegistro;
        this.dataHoraRegistro = LocalDateTime.now();
        this.status = StatusServico.PENDENTE;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }

    public String getCliente() {
        return cliente;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public String getPlacaCavalo() {
        return placaCavalo;
    }

    public String getPlacaCarreta() {
        return placaCarreta;
    }

    public int getNotaFiscal() {
        return notaFiscal;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public LocalDateTime getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public String getOperadorRegistro() {
        return operadorRegistro;
    }

    public StatusServico getStatus() {
        return status;
    }
}