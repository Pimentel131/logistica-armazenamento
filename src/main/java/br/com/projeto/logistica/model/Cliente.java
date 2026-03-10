package br.com.projeto.logistica.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
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
}
