package br.com.projeto.logistica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "cargas")
public class Carga {
    //DADOS PRINCÍPAIS
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private String nomeMotorista;
    private String placaCavalo;
    private String placaCarreta;
    private String materialProduto;
    private String unidade;
    private int notaFiscal;
    private String operadorResponsavel;

    @Setter
    private int quantidade;
    @Setter
    private int galpao;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String codigoQr;

    //CONTROLE
    private boolean aindaArmazenado = true;
    private LocalDate dataEntrada;

    //SAÍDA
    @Embedded
    private Saida saida;

    //RETRABALHO
    @Embedded
    private Retrabalho retrabalho;

    public Carga() {
    }

    public Carga(Cliente cliente, String nomeMotorista, String placaCavalo, String placaCarreta, String materialProduto,
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
    }

    @PrePersist
    public void gerarQrCode() {
        if (this.codigoQr == null) {
            this.codigoQr = UUID.randomUUID().toString();
        }
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
}
