package br.com.projeto.logistica.model;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public record Saida(LocalDateTime dataHoraSaida,
                    String motoristaSaida,
                    String cavaloSaida,
                    String carretaSaida,
                    String operadorSaida) {


    public Saida(String motoristaSaida, String cavaloSaida, String carretaSaida, String operadorSaida) {
        this(LocalDateTime.now(), motoristaSaida, cavaloSaida, carretaSaida, operadorSaida);
    }
}
