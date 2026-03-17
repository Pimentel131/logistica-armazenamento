package br.com.projeto.logistica.validator;

import br.com.projeto.logistica.model.Carga;

public class CargaValidator {

    public static void validarCargaExiste(Carga carga) {
        if (carga == null) {
            throw new IllegalStateException("Carga não encontrada.");
        }
    }

    public static void validarCargaArmazenada(Carga carga) {
        if (!carga.isAindaArmazenado()) {
            throw new IllegalStateException("Carga já foi expedida.");
        }
    }
}