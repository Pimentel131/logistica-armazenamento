package br.com.projeto.logistica.repository;

import br.com.projeto.logistica.model.ServicoDiario;
import br.com.projeto.logistica.model.StatusServico;
import br.com.projeto.logistica.model.TipoOperacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicoDiarioRepository extends JpaRepository<ServicoDiario, Long> {
    Optional<ServicoDiario> findByNotaFiscalAndTipoOperacao(int notaFiscal, TipoOperacao tipoOperacao);
    Optional<ServicoDiario> findByNotaFiscalAndTipoOperacaoAndStatusNot(int notaFiscal, TipoOperacao tipoOperacao,
                                                                     StatusServico statusServico);
    Optional<ServicoDiario> findByNotaFiscalAndStatus(int notaFiscal, StatusServico status);
}
