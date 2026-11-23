package br.com.farmacia.app;

import br.com.farmacia.builder.VendaBuilder;
import br.com.farmacia.model.Cliente;
import br.com.farmacia.model.Produto;
import br.com.farmacia.model.Venda;
import br.com.farmacia.strategy.CalculoComTaxaEntrega;
import br.com.farmacia.strategy.CalculoPadrao;
import br.com.farmacia.strategy.EstrategiaCalculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Produto> produtos = new ArrayList<>();
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Venda> vendas = new ArrayList<>();

    private static int proximoIdProduto = 1;
    private static int proximoIdCliente = 1;

    public static void main(String[] args) {
        inicializarDados();

        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;

            do {
                exibirMenu();
                opcao = lerInteiro(scanner, "Escolha uma opção: ");

                switch (opcao) {
                    case 1 -> listarProdutos();
                    case 2 -> cadastrarProduto(scanner);
                    case 3 -> listarClientes();
                    case 4 -> cadastrarCliente(scanner);
                    case 5 -> registrarVenda(scanner);
                    case 6 -> listarVendas();
                    case 0 -> System.out.println("Encerrando o sistema. Até logo!");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 0);
        }
    }

    private static void inicializarDados() {
        produtos.add(new Produto(proximoIdProduto++, "Dipirona 500mg", 8.50));
        produtos.add(new Produto(proximoIdProduto++, "Paracetamol 750mg", 12.00));
        produtos.add(new Produto(proximoIdProduto++, "Vitamina C 1g", 24.90));
        produtos.add(new Produto(proximoIdProduto++, "Protetor Solar FPS 50", 65.00));

        clientes.add(new Cliente(proximoIdCliente++, "Ana Paula Souza", "111.222.333-44"));
        clientes.add(new Cliente(proximoIdCliente++, "Carlos Eduardo Lima", "555.666.777-88"));
        clientes.add(new Cliente(proximoIdCliente++, "Mariana Rocha", "999.888.777-66"));
    }

    private static void exibirMenu() {
        System.out.println("\n=== Sistema de Farmácia ===");
        System.out.println("1. Listar produtos");
        System.out.println("2. Cadastrar produto");
        System.out.println("3. Listar clientes");
        System.out.println("4. Cadastrar cliente");
        System.out.println("5. Registrar venda");
        System.out.println("6. Listar vendas realizadas");
        System.out.println("0. Sair");
    }

    private static void listarProdutos() {
        System.out.println("\n--- Produtos Disponíveis ---");
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    private static void cadastrarProduto(Scanner scanner) {
        System.out.println("\n--- Cadastrar Novo Produto ---");
        String nome = lerTextoObrigatorio(scanner, "Nome do produto: ");
        double preco = lerDoubleNaoNegativo(scanner, "Preço (ex.: 19.90): ");

        Produto produto = new Produto(proximoIdProduto++, nome, preco);
        produtos.add(produto);
        System.out.println("Produto cadastrado com sucesso: " + produto);
    }

    private static void listarClientes() {
        System.out.println("\n--- Clientes Cadastrados ---");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.println("\n--- Cadastrar Novo Cliente ---");
        String nome = lerTextoObrigatorio(scanner, "Nome do cliente: ");
        String cpf = lerTextoObrigatorio(scanner, "CPF do cliente: ");

        Cliente cliente = new Cliente(proximoIdCliente++, nome, cpf);
        clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso: " + cliente);
    }

    private static void registrarVenda(Scanner scanner) {
        System.out.println("\n--- Registrar Nova Venda ---");
        listarClientes();

        Cliente cliente = null;
        while (cliente == null) {
            int idCliente = lerInteiro(scanner, "Informe o ID do cliente: ");
            cliente = buscarClientePorId(idCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Tente novamente.");
            }
        }

        VendaBuilder builder = new VendaBuilder().comCliente(cliente);

        boolean adicionarMais = true;
        while (adicionarMais) {
            listarProdutos();

            Produto produto = null;
            while (produto == null) {
                int idProduto = lerInteiro(scanner, "Informe o ID do produto: ");
                produto = buscarProdutoPorId(idProduto);
                if (produto == null) {
                    System.out.println("Produto não encontrado. Tente novamente.");
                }
            }

            int quantidade;
            while (true) {
                quantidade = lerInteiro(scanner, "Informe a quantidade: ");
                if (quantidade > 0) {
                    break;
                }
                System.out.println("A quantidade deve ser maior que zero.");
            }

            builder.adicionarItem(produto, quantidade);
            System.out.println("Item adicionado com sucesso!");

            System.out.print("Deseja adicionar outro produto? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            adicionarMais = resposta.equals("s") || resposta.equals("sim");
        }

        EstrategiaCalculo estrategia = escolherEstrategia(scanner);
        builder.comEstrategia(estrategia);

        System.out.print("Observação (opcional, pressione Enter para pular): ");
        String observacao = scanner.nextLine();
        if (!observacao.isBlank()) {
            builder.comObservacao(observacao);
        }

        Venda venda = builder.build();
        vendas.add(venda);

        System.out.println("\nVenda registrada com sucesso!");
        System.out.println("----------------------------");
        System.out.println(venda);
        System.out.println("----------------------------");
    }

    private static EstrategiaCalculo escolherEstrategia(Scanner scanner) {
        System.out.println("\nEscolha a forma de cálculo:");
        System.out.println("1. Cálculo Padrão (Balcão)");
        System.out.printf("2. Entrega (taxa padrão de R$ %.2f)%n", CalculoComTaxaEntrega.TAXA_PADRAO);
        System.out.println("3. Entrega (informar taxa personalizada)");

        while (true) {
            int opcao = lerInteiro(scanner, "Opção de cálculo: ");
            switch (opcao) {
                case 1 -> {
                    return new CalculoPadrao();
                }
                case 2 -> {
                    return new CalculoComTaxaEntrega();
                }
                case 3 -> {
                    double taxa = lerDoubleNaoNegativo(scanner, "Informe o valor da taxa de entrega (ex.: 15.00): ");
                    return new CalculoComTaxaEntrega(taxa);
                }
                default -> System.out.println("Opção inválida. Escolha 1, 2 ou 3.");
            }
        }
    }

    private static void listarVendas() {
        System.out.println("\n--- Vendas Realizadas ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
            return;
        }

        for (int i = 0; i < vendas.size(); i++) {
            System.out.printf("%nVenda #%d%n", i + 1);
            System.out.println("----------------------------");
            System.out.println(vendas.get(i));
            System.out.println("----------------------------");
        }
    }

    private static Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    private static Produto buscarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    private static String lerTextoObrigatorio(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isBlank()) {
                return entrada;
            }
            System.out.println("Este campo é obrigatório. Tente novamente.");
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static double lerDoubleNaoNegativo(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(entrada);
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("O valor não pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número válido.");
            }
        }
    }
}
