package br.com.projeto.logistica.model;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public record Retrabalho(LocalDateTime dataHoraRetrabalho,
                         int quantidadeRetrabalhado,
                         int quantidadeStrechado,
                         boolean strechDuplo,
                         boolean ficouArmazenado) {

    public Retrabalho(int quantidadeRetrabalhado, int quantidadeStrechado, boolean strechDuplo, boolean ficouArmazenado) {
        this(LocalDateTime.now(), quantidadeRetrabalhado, quantidadeStrechado, strechDuplo, ficouArmazenado);
    }
}
