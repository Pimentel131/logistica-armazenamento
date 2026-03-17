package br.com.projeto.logistica.service;

import br.com.projeto.logistica.model.*;
import br.com.projeto.logistica.repository.CargaRepository;
import br.com.projeto.logistica.repository.ServicoDiarioRepository;
import br.com.projeto.logistica.validator.CargaValidator;
import br.com.projeto.logistica.validator.ServicoDiarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoDiarioService {
    @Autowired
    private ServicoDiarioRepository servicoDiarioRepository;

    @Autowired
    private CargaService cargaService;

    @Autowired
    private ClienteService clienteService;

    public ServicoDiario registrarServico(String cliente, String nomeMotorista, String placaCavalo, String placaCarreta,
                                          int notaFiscal, TipoOperacao tipoOperacao, String operadorRegistro) {
        clienteService.buscarCliente(cliente);

        ServicoDiario servico = new ServicoDiario(cliente, nomeMotorista, placaCavalo, placaCarreta, notaFiscal,
                tipoOperacao, operadorRegistro);

        return servicoDiarioRepository.save(servico);
    }

    public ServicoDiario registrarSaida(String cliente, String nomeMotorista, String placaCavalo,
                                        String placaCarreta, int notaFiscal, String operadorRegistro) {
        clienteService.buscarCliente(cliente);

        Carga carga = this.cargaService.buscarCargaPorNF(cliente, notaFiscal);

        CargaValidator.validarCargaExiste(carga);

        ServicoDiario servico = new ServicoDiario(cliente, nomeMotorista, placaCavalo,
                placaCarreta, notaFiscal,
                TipoOperacao.SAIDA, operadorRegistro);
        return servicoDiarioRepository.save(servico);
    }

    public List<ServicoDiario> listarServicoDiario() {
        return servicoDiarioRepository.findAll();
    }

    public ServicoDiario cancelarOperacaoNF(int notaFiscal, TipoOperacao tipo) {
        ServicoDiario servico = buscarServicoPorNF(notaFiscal, tipo);

        ServicoDiarioValidator.validarServicoExiste(servico);
        ServicoDiarioValidator.validarNaoCancelado(servico);
        ServicoDiarioValidator.validarNaoConcluido(servico);

        servico.setStatus(StatusServico.CANCELADO);
        return servicoDiarioRepository.save(servico);
    }


    public ServicoDiario iniciarOperacao(int notaFiscal, TipoOperacao tipo) {

        ServicoDiario servico = buscarServicoPorNF(notaFiscal, tipo);

        ServicoDiarioValidator.validarServicoExiste(servico);
        ServicoDiarioValidator.validarNaoCancelado(servico);
        ServicoDiarioValidator.validarNaoConcluido(servico);

        if (tipo == TipoOperacao.SAIDA) {
            cargaService.validarCargaParaSaida(servico.getCliente(), servico.getNotaFiscal());
            servico.setStatus(StatusServico.AGUARDANDO_QR);
        } else {
            servico.setStatus(StatusServico.EM_ANDAMENTO);
        }

        return servicoDiarioRepository.save(servico);
    }

    public ServicoDiario buscarServicoPorNF(int notaFiscal, TipoOperacao tipoOperacao) {
        return servicoDiarioRepository.findByNotaFiscalAndTipoOperacao(notaFiscal, tipoOperacao)
                .orElse(null);
    }

    public ServicoDiario buscarServicoPorNFAtivo(int notaFiscal, TipoOperacao tipoOperacao) {
        ServicoDiario servico = servicoDiarioRepository
                .findByNotaFiscalAndTipoOperacaoAndStatusNot(notaFiscal, tipoOperacao, StatusServico.CONCLUIDO)
                .orElse(null);

        if (servico != null && servico.getStatus() == StatusServico.CANCELADO) {
            return null;
        }
        return servico;
    }
}
