package br.com.projeto.logistica.service;

import br.com.projeto.logistica.model.*;
import br.com.projeto.logistica.repository.CargaRepository;
import br.com.projeto.logistica.repository.ServicoDiarioRepository;
import br.com.projeto.logistica.validator.CargaValidator;
import br.com.projeto.logistica.validator.ServicoDiarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperacaoService {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CargaService cargaService;

    @Autowired
    private ServicoDiarioService servicoDiarioService;

    @Autowired
    private ServicoDiarioRepository servicoDiarioRepository;

    @Autowired
    private CargaRepository cargaRepository;

    public Carga concluirSaida(int notaFiscal, String operadorSaida, TipoOperacao tipo) {
        ServicoDiario servico = servicoDiarioService.buscarServicoPorNFAtivo(notaFiscal, tipo);

        ServicoDiarioValidator.validarServicoExiste(servico);
        ServicoDiarioValidator.validarTipo(servico, TipoOperacao.SAIDA);
        ServicoDiarioValidator.validarOperacaoIniciada(servico);

        Carga carga = this.cargaService.buscarCargaPorNF(servico.getCliente(), servico.getNotaFiscal());

        CargaValidator.validarCargaExiste(carga);

        carga.registrarSaida(servico.getNomeMotorista(), servico.getPlacaCavalo(),
                servico.getPlacaCarreta(), operadorSaida);
        servico.setStatus(StatusServico.CONCLUIDO);
        servicoDiarioRepository.save(servico);
        return cargaRepository.save(carga);
    }

    public void concluirEntrada(int notaFiscal, String materialProduto, int quantidade,
                                String unidade, int galpao, String operadorResponsavel,
                                Integer quantidadeRetrabalhado, Integer quantidadeStrechado,
                                Boolean strechDuplo, Boolean ficouArmazenado,
                                TipoOperacao tipo) {
        ServicoDiario servico = servicoDiarioService.buscarServicoPorNFAtivo(notaFiscal, tipo);

        ServicoDiarioValidator.validarServicoExiste(servico);
        ServicoDiarioValidator.validarTipo(servico, tipo);
        ServicoDiarioValidator.validarNaoCancelado(servico);
        ServicoDiarioValidator.validarOperacaoIniciada(servico);
        ServicoDiarioValidator.validarNaoConcluido(servico);

        Cliente cliente = clienteService.buscarCliente(servico.getCliente());

        Carga carga = new Carga(
                cliente, servico.getNomeMotorista(), servico.getPlacaCavalo(), servico.getPlacaCarreta(),
                materialProduto, quantidade, unidade, notaFiscal, operadorResponsavel, galpao);

        cargaService.adicionarCarga(carga);

        if (tipo == TipoOperacao.RETRABALHO) {cargaService.registrarDadosRetrabalho(carga, quantidadeRetrabalhado,
                                              quantidadeStrechado, strechDuplo, ficouArmazenado);
        }

        servico.setStatus(StatusServico.CONCLUIDO);
        servicoDiarioRepository.save(servico);
    }

    public void processarLeituraQr(int notaFiscal, String codigoQr, String operador) {
        ServicoDiario servico = servicoDiarioService.buscarServicoPorNFAtivo(
                notaFiscal,
                TipoOperacao.SAIDA
        );

        ServicoDiarioValidator.validarServicoExiste(servico);
        ServicoDiarioValidator.validarTipo(servico, TipoOperacao.SAIDA);

        Carga carga = cargaService.buscarCargaPorNF(servico.getCliente(), servico.getNotaFiscal());

        CargaValidator.validarCargaExiste(carga);
        CargaValidator.validarCargaArmazenada(carga);

        if (!codigoQr.equals(carga.getCodigoQr())) {
            throw new IllegalStateException("QR não corresponde à carga desta operação.");
        }

        if (servico.getStatus() == StatusServico.AGUARDANDO_QR) {
            servico.setStatus(StatusServico.EM_ANDAMENTO);
            servicoDiarioRepository.save(servico);
        } else if (servico.getStatus() == StatusServico.EM_ANDAMENTO) {
            concluirSaida(servico.getNotaFiscal(), operador, TipoOperacao.SAIDA);
        } else {
            throw new IllegalStateException("QR inválido para o status atual.");
        }
    }
}
