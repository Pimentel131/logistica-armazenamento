package br.com.projeto.logistica.validator;

import br.com.projeto.logistica.model.ServicoDiario;
import br.com.projeto.logistica.model.StatusServico;
import br.com.projeto.logistica.model.TipoOperacao;

public class ServicoDiarioValidator {

    public static void validarServicoExiste(ServicoDiario servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Operação não encontrada.");
        }
    }

    public static void validarOperacaoIniciada(ServicoDiario servico) {
        if (servico.getStatus() == StatusServico.PENDENTE) {
            throw new IllegalStateException("Operação ainda não foi iniciada.");
        }
    }

    public static void validarNaoCancelado(ServicoDiario servico) {
        if (servico.getStatus() == StatusServico.CANCELADO) {
            throw new IllegalStateException("Operação cancelada.");
        }
    }

    public static void validarNaoConcluido(ServicoDiario servico) {
        if (servico.getStatus() == StatusServico.CONCLUIDO) {
            throw new IllegalStateException("Operação já concluída.");
        }
    }

    public static void validarTipo(ServicoDiario servico, TipoOperacao tipo) {
        if (servico.getTipoOperacao() != tipo) {
            throw new IllegalStateException("Tipo de operação inválido.");
        }
    }


}