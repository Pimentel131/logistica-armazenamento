package br.com.projeto.logistica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "servicos_diarios")
public class ServicoDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private StatusServico status;

    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao; // "ENTRADA", "SAIDA" ou "RETRABALHO"

    private String cliente;
    private String nomeMotorista;
    private String placaCavalo;
    private String placaCarreta; // pode ser null se for truck
    private int notaFiscal;
    private LocalDateTime dataHoraRegistro;
    private String operadorRegistro;

    public ServicoDiario() {
    }

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
}