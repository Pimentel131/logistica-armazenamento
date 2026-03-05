package br.com.projeto.logistica.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Carga {
    //DADOS PRINCÍPAIS
    private String cliente;
    private String nomeMotorista;
    private String placaCavalo;
    private String placaCarreta;
    private String materialProduto;
    private int quantidade;
    private String unidade;
    private int notaFiscal;
    private String operadorResponsavel;
    private int galpao;
    private UUID id;
    private String codigoQr;

    //CONTROLE
    private boolean aindaArmazenado = true;
    private LocalDate dataEntrada;

    //SAÍDA
    private Saida saida;

    //RETRABALHO
    private Retrabalho retrabalho;

    public Carga(String cliente, String nomeMotorista, String placaCavalo, String placaCarreta, String materialProduto,
                 int quantidade, String unidade, int notaFiscal, String operadorResponsavel, int galpao) {
        this.cliente = cliente;
        this.nomeMotorista = nomeMotorista;
        this.placaCavalo = placaCavalo;
        this.placaCarreta = placaCarreta;
        this.materialProduto = materialProduto;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.notaFiscal = notaFiscal;
        this.operadorResponsavel = operadorResponsavel;
        this.galpao = galpao;
        this.aindaArmazenado = true;
        this.dataEntrada = LocalDate.now();
        this.id = UUID.randomUUID();
        this.codigoQr = id.toString();
    }

    public void registrarSaida(String motoristaSaida, String cavaloSaida,
                               String carretaSaida, String operadorSaida) {
        if (this.saida != null) {
            throw new IllegalStateException("Carga já possui saída registrada!");
        }
        this.saida = new Saida(motoristaSaida, cavaloSaida, carretaSaida, operadorSaida);
        this.aindaArmazenado = false;
    }

    public void registrarRetrabalho(int quantidadeRetrabalhado, int quantidadeStrechado,
                                    boolean strechDuplo, boolean ficouArmazenado) {
        if (this.retrabalho != null) {
            throw new IllegalStateException("Carga já possui retrabalho registrado!");
        }
        this.retrabalho = new Retrabalho(quantidadeRetrabalhado, quantidadeStrechado,
                strechDuplo, ficouArmazenado);

        if (!ficouArmazenado) {
            this.registrarSaida(this.nomeMotorista, this.placaCavalo,
                    this.placaCarreta, "RETRABALHO");
        }
    }

    public boolean temSaida() {
        return this.saida != null;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setGalpao(int galpao) {
        this.galpao = galpao;
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

    public String getMaterialProduto() {
        return materialProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public int getNotaFiscal() {
        return notaFiscal;
    }

    public String getOperadorResponsavel() {
        return operadorResponsavel;
    }

    public int getGalpao() {
        return galpao;
    }

    public boolean isAindaArmazenado() {
        return aindaArmazenado;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public Saida getSaida() {
        return saida;
    }

    public Retrabalho getRetrabalho() {
        return retrabalho;
    }

    public UUID getId() {
        return id;
    }

    public String getCodigoQr() {
        return codigoQr;
    }
}
