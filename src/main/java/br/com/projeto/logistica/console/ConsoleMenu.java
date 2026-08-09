package br.com.projeto.logistica.console;

import br.com.projeto.logistica.model.*;
import br.com.projeto.logistica.service.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu implements CommandLineRunner {

    private final ClienteService clienteService;
    private final ServicoDiarioService servicoDiarioService;
    private final OperacaoService operacaoService;
    private final CargaService cargaService;

    public ConsoleMenu(
            ClienteService clienteService,
            ServicoDiarioService servicoDiarioService,
            OperacaoService operacaoService,
            CargaService cargaService
    ) {
        this.clienteService = clienteService;
        this.servicoDiarioService = servicoDiarioService;
        this.operacaoService = operacaoService;
        this.cargaService = cargaService;
    }

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== SISTEMA LOGISTICA =====");
            System.out.println("1 - Criar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Criar serviço diário");
            System.out.println("4 - Iniciar operação");
            System.out.println("5 - Cancelar serviço");
            System.out.println("6 - Concluir entrada");
            System.out.println("7 - Processar leitura QR (saída)");
            System.out.println("8 - Listar cargas");
            System.out.println("9 - Listar serviços do dia");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (opcao) {

                    case 1 -> criarCliente(scanner);

                    case 2 -> listarClientes();

                    case 3 -> criarServico(scanner);

                    case 4 -> iniciarOperacao(scanner);

                    case 5 -> cancelarServico(scanner);

                    case 6 -> concluirEntrada(scanner);

                    case 7 -> processarQr(scanner);

                    case 8 -> listarCargas(scanner);

                    case 9 -> listarServicos();

                    case 0 -> {
                        System.out.println("Encerrando sistema...");
                        return;
                    }

                    default -> System.out.println("Opção inválida");

                }

            } catch (Exception e) {

                System.out.println("Erro: " + e.getMessage());

            }

        }

    }

    private void iniciarOperacao(Scanner scanner) {

        System.out.println("Nota fiscal:");
        int nf = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Tipo operação (ENTRADA / SAIDA / RETRABALHO):");
        TipoOperacao tipo = TipoOperacao.valueOf(scanner.nextLine().toUpperCase());

        servicoDiarioService.iniciarOperacao(nf, tipo);

        System.out.println("Operação iniciada!");

    }

    private void criarCliente(Scanner scanner) {

        System.out.println("Nome do cliente:");
        String nome = scanner.nextLine();

        clienteService.cadastrarCliente(nome);

        System.out.println("Cliente criado com sucesso!");

    }

    private void listarClientes() {

        List<Cliente> clientes = clienteService.listarClientes();

        clientes.forEach(cliente ->
                System.out.println(cliente.getId() + " - " + cliente.getNome())
        );

    }

    private void criarServico(Scanner scanner) {

        System.out.println("Nome do cliente:");
        String nomeCliente = scanner.nextLine();
        Cliente cliente = clienteService.buscarCliente(nomeCliente);

        System.out.println("Nota fiscal:");
        int nf = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Motorista:");
        String motorista = scanner.nextLine();

        System.out.println("Placa cavalo:");
        String cavalo = scanner.nextLine();

        System.out.println("Placa carreta:");
        String carreta = scanner.nextLine();

        System.out.println("Tipo operação (ENTRADA / SAIDA):");
        TipoOperacao tipo = TipoOperacao.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Operador: ");
        String operador = scanner.nextLine();

        servicoDiarioService.registrarServico(
                cliente, motorista, cavalo, carreta, nf, tipo, operador);

        System.out.println("Serviço criado!");

    }

    private void cancelarServico(Scanner scanner) {

        System.out.println("Nota fiscal do serviço:");
        int nf = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Tipo operação (ENTRADA / SAIDA):");
        TipoOperacao tipo = TipoOperacao.valueOf(scanner.nextLine().toUpperCase());

        servicoDiarioService.cancelarOperacaoNF(nf, tipo);

        System.out.println("Serviço cancelado!");

    }

    private void concluirEntrada(Scanner scanner) {

        System.out.println("Nota fiscal:");
        int nf = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Material / Produto:");
        String produto = scanner.nextLine();

        System.out.println("Quantidade:");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Unidade:");
        String unidade = scanner.nextLine();

        System.out.println("Galpão:");
        int galpao = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Operador responsável:");
        String operador = scanner.nextLine();

        System.out.println("Tipo operação (ENTRADA ou RETRABALHO):");
        TipoOperacao tipo = TipoOperacao.valueOf(scanner.nextLine().toUpperCase());

        Integer quantidadeRetrabalhado = null;
        Integer quantidadeStrechado = null;
        Boolean strechDuplo = null;
        Boolean ficouArmazenado = null;

        if (tipo == TipoOperacao.RETRABALHO) {

            System.out.println("Quantidade retrabalhado:");
            quantidadeRetrabalhado = scanner.nextInt();

            System.out.println("Quantidade strechado:");
            quantidadeStrechado = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Strech duplo (true/false):");
            strechDuplo = scanner.nextBoolean();

            System.out.println("Ficou armazenado (true/false):");
            ficouArmazenado = scanner.nextBoolean();

            scanner.nextLine();
        }

        operacaoService.concluirEntrada(nf, produto, quantidade, unidade, galpao, operador, quantidadeRetrabalhado,
                quantidadeStrechado, strechDuplo, ficouArmazenado, tipo);

        System.out.println("Entrada concluída com sucesso!");

    }

    private void processarQr(Scanner scanner) {

        System.out.println("Nota fiscal:");
        int nf = scanner.nextInt();
        scanner.nextLine();

        System.out.println("QR code:");
        String qr = scanner.nextLine();

        System.out.println("Operador:");
        String operador = scanner.nextLine();

        operacaoService.processarLeituraQr(
                nf,
                qr,
                operador
        );

        System.out.println("Leitura processada!");

    }

    private void listarCargas(Scanner scanner) {

        System.out.println("Cliente: ");
        String cliente = scanner.nextLine();

        List<Carga> cargas = clienteService.obterCargasDoCliente(cliente);

        cargas.forEach(c ->
                System.out.println(
                        c.getNotaFiscal() + " | " +
                                c.getCliente().getNome() + " | " +
                                c.getMaterialProduto() + " | " +
                                c.getGalpao() + " | " +
                                c.getCodigoQr()
                )
        );

    }

    private void listarServicos() {

        List<ServicoDiario> servicos = servicoDiarioService.listarServicoDiario();

        servicos.forEach(s ->
                System.out.println(
                        s.getNotaFiscal() + " | " +
                                s.getCliente() + " | " +
                                s.getTipoOperacao() + " | " +
                                s.getStatus()
                )
        );

    }

}