package br.com.projeto.logistica.service;

import br.com.projeto.logistica.model.Carga;
import br.com.projeto.logistica.model.Cliente;
import br.com.projeto.logistica.repository.CargaRepository;
import br.com.projeto.logistica.repository.ClienteRepository;
import br.com.projeto.logistica.validator.CargaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargaService {
    @Autowired
    private CargaRepository cargaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Carga validarCargaParaSaida(Cliente cliente, int notaFiscal) {

        Carga carga = buscarCargaPorNF(cliente, notaFiscal);

        CargaValidator.validarCargaExiste(carga);
        CargaValidator.validarCargaArmazenada(carga);

        return carga;
    }

    public void registrarDadosRetrabalho(Carga carga,int quantidadeRetrabalhado, int quantidadeStrechado,
                                          boolean strechDuplo, boolean ficouArmazenado) {
        carga.registrarRetrabalho(quantidadeRetrabalhado, quantidadeStrechado, strechDuplo, ficouArmazenado);

    }


    public Carga buscarCargaPorNF(Cliente cliente, int notaFiscal) {
        return cargaRepository.findByClienteAndNotaFiscalAndAindaArmazenado(cliente, notaFiscal, true)
                .orElse(null);
    }

    public Carga buscarCargaPorNFGeral(int notaFiscal) {
        return cargaRepository.findByNotaFiscalAndAindaArmazenado(notaFiscal, true)
                .orElse(null);
    }

    public Carga adicionarCarga(Carga carga) {
        return cargaRepository.save(carga);
    }

}
