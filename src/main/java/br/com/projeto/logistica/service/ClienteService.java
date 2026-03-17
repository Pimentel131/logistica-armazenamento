package br.com.projeto.logistica.service;

import br.com.projeto.logistica.model.Cliente;
import br.com.projeto.logistica.model.Carga;
import br.com.projeto.logistica.repository.ClienteRepository;
import br.com.projeto.logistica.repository.CargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CargaRepository cargaRepository;

    public Cliente buscarCliente(String nomeCliente) {

        return clienteRepository.findByNome(nomeCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não cadastrado"));
    }

    public Cliente cadastrarCliente(String nomeCliente) {
        Optional<Cliente> existente = clienteRepository.findByNome(nomeCliente);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Cliente já cadastrado");
        }

        Cliente cliente = new Cliente(nomeCliente);
        return clienteRepository.save(cliente);
    }


    public List<Carga> obterCargasDoCliente(String nomeCliente) {
        Cliente cliente = clienteRepository.findByNome(nomeCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não cadastrado: " + nomeCliente));

        return cargaRepository.findByCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public void listarCargasCliente(String nomeCliente) {
        List<Carga> cargas = obterCargasDoCliente(nomeCliente);

        System.out.println("\n===== CARGAS DO CLIENTE: " + nomeCliente + " =====");

        if (cargas.isEmpty()) {
            System.out.println("Nenhuma carga encontrada.");
            return;
        }

        for (Carga carga : cargas) {
            String status = carga.isAindaArmazenado() ? "ARMAZENADO" : "SAIU";
            System.out.println("NF: " + carga.getNotaFiscal() +
                    "  |  Material: " + carga.getMaterialProduto() +
                    "  |  Qtd: " + carga.getQuantidade() + " " + carga.getUnidade() +
                    "  |  Galpão: " + carga.getGalpao() +
                    "  |  Status: " + status +
                    "  |  ID: " + carga.getId());
        }
    }
}
