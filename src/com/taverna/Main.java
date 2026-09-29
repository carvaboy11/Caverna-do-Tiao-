package com.taverna;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.taverna.model.Cliente;
import com.taverna.model.Item;
import com.taverna.model.Pedido;
import com.taverna.repository.ItemRepository;
import com.taverna.repository.EstoqueRepository;
import com.taverna.service.TavernaService;
import com.taverna.factory.ItemFactory;
import com.taverna.singleton.CaixaRegistradora;
import com.taverna.exception.ItemIndisponivelException;
import com.taverna.exception.SaldoInsuficienteException;

public class Main {

    public static void main(String[] args) {
        ItemRepository repositorio = new EstoqueRepository();
        popularCardapio(repositorio);

        TavernaService taverna = new TavernaService(repositorio);

        List<Cliente> clientes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Bem-vindo à Taverna do Tião ====");

        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    exibirCardapio(taverna);
                    break;
                case "2":
                    cadastrarCliente(scanner, clientes);
                    break;
                case "3":
                    fazerPedido(scanner, taverna, clientes);
                    break;
                case "4":
                    exibirCaixa();
                    break;
                case "5":
                    cadastrarItem(scanner, taverna);
                    break;
                case "6":
                    adicionarSaldo(scanner, clientes);
                    break;
                case "0":
                    rodando = false;
                    System.out.println("Até a próxima, viajante!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    private static void popularCardapio(ItemRepository repositorio) {
        repositorio.adicionar(ItemFactory.criarBebida("Cerveja escura", 8.50, 20, true));
        repositorio.adicionar(ItemFactory.criarBebida("Hidromel", 12.00, 10, true));
        repositorio.adicionar(ItemFactory.criarBebida("Suco de maçã", 5.00, 15, false));
        repositorio.adicionar(ItemFactory.criarComida("Ensopado de javali", 18.00, 8, 20));
        repositorio.adicionar(ItemFactory.criarComida("Pão com queijo", 6.00, 12, 5));
    }

    private static void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1 - Ver cardápio");
        System.out.println("2 - Cadastrar cliente");
        System.out.println("3 - Fazer pedido");
        System.out.println("4 - Ver caixa da taverna");
        System.out.println("5 - Cadastrar novo item no cardápio");
        System.out.println("6 - Adicionar saldo a um cliente");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static void exibirCardapio(TavernaService taverna) {
        System.out.println("\n--- Cardápio ---");
        for (Item item : taverna.consultarCardapio()) {
            System.out.println(item);
        }
    }

    private static void cadastrarCliente(Scanner scanner, List<Cliente> clientes) {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Saldo inicial (moedas): ");
        double saldo;
        try {
            saldo = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, usando saldo 0.");
            saldo = 0;
        }
        clientes.add(new Cliente(nome, saldo));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void fazerPedido(Scanner scanner, TavernaService taverna, List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("Cadastre um cliente primeiro (opção 2).");
            return;
        }

        System.out.println("Clientes: ");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(i + " - " + clientes.get(i));
        }
        System.out.print("Escolha o índice do cliente: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine().trim());
            Cliente cliente = clientes.get(indice);

            System.out.print("Itens desejados (separados por vírgula): ");
            String linha = scanner.nextLine();
            List<String> nomesItens = new ArrayList<>();
            for (String parte : linha.split(",")) {
                nomesItens.add(parte.trim());
            }

            try {
                Pedido pedido = taverna.registrarPedido(cliente, nomesItens);
                System.out.printf("Pedido concluído! Total: R$ %.2f. Saldo restante: %.2f%n",
                    pedido.calcularTotal(), cliente.getSaldo());
            } catch (ItemIndisponivelException | SaldoInsuficienteException e) {
                System.out.println("Não foi possível concluir o pedido: " + e.getMessage());
            }

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Índice de cliente inválido.");
        }
    }

    private static void cadastrarItem(Scanner scanner, TavernaService taverna) {
        System.out.print("Tipo do item (bebida/comida): ");
        String tipo = scanner.nextLine().trim();
        System.out.print("Nome do item: ");
        String nome = scanner.nextLine().trim();

        try {
            System.out.print("Preço: ");
            double preco = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Quantidade em estoque: ");
            int estoque = Integer.parseInt(scanner.nextLine().trim());

            Item item;
            if (tipo.equalsIgnoreCase("bebida")) {
                System.out.print("É alcoólica? (s/n): ");
                boolean alcoolica = scanner.nextLine().trim().equalsIgnoreCase("s");
                item = ItemFactory.criarBebida(nome, preco, estoque, alcoolica);
            } else if (tipo.equalsIgnoreCase("comida")) {
                System.out.print("Tempo de preparo (minutos): ");
                int tempoPreparo = Integer.parseInt(scanner.nextLine().trim());
                item = ItemFactory.criarComida(nome, preco, estoque, tempoPreparo);
            } else {
                System.out.println("Tipo inválido. Use \"bebida\" ou \"comida\".");
                return;
            }

            taverna.cadastrarItem(item);
            System.out.println("Item cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido. Cadastro cancelado.");
        }
    }

    private static void adicionarSaldo(Scanner scanner, List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("Cadastre um cliente primeiro (opção 2).");
            return;
        }

        System.out.println("Clientes: ");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(i + " - " + clientes.get(i));
        }
        System.out.print("Escolha o índice do cliente: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine().trim());
            Cliente cliente = clientes.get(indice);

            System.out.print("Valor a adicionar: ");
            double valor = Double.parseDouble(scanner.nextLine().trim());

            try {
                cliente.adicionarSaldo(valor);
                System.out.printf("Saldo atualizado! Novo saldo de %s: %.2f%n", cliente.getNome(), cliente.getSaldo());
            } catch (IllegalArgumentException e) {
                System.out.println("Não foi possível adicionar saldo: " + e.getMessage());
            }

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void exibirCaixa() {
        CaixaRegistradora caixa = CaixaRegistradora.getInstance();
        System.out.printf("\nTotal arrecadado: R$ %.2f%n", caixa.getTotalArrecadado());
        System.out.println("Histórico de vendas:");
        for (String venda : caixa.getHistoricoVendas()) {
            System.out.println(" - " + venda);
        }
    }
}
