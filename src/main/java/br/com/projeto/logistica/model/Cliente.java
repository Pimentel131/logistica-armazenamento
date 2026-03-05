package br.com.projeto.logistica.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataCadastro;

    public Cliente() {}

    public Cliente(String nome) {
        this.nome = nome;
        this.dataCadastro = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
}
