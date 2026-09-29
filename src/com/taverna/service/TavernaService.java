package com.taverna.service;

import java.util.List;
import com.taverna.model.Cliente;
import com.taverna.model.Item;
import com.taverna.model.Pedido;
import com.taverna.repository.ItemRepository;
import com.taverna.singleton.CaixaRegistradora;
import com.taverna.exception.ItemIndisponivelException;
import com.taverna.exception.SaldoInsuficienteException;

public class TavernaService {

    private final ItemRepository repositorio;
    private final CaixaRegistradora caixa;

    public TavernaService(ItemRepository repositorio) {
        this.repositorio = repositorio;
        this.caixa = CaixaRegistradora.getInstance();
    }

    public List<Item> consultarCardapio() {
        return repositorio.listarTodos();
    }

    public void cadastrarItem(Item item) {
        repositorio.adicionar(item);
    }

    public Pedido registrarPedido(Cliente cliente, List<String> nomesItens)
            throws ItemIndisponivelException, SaldoInsuficienteException {

        Pedido pedido = new Pedido(cliente);

        for (String nome : nomesItens) {
            Item item = repositorio.buscarPorNome(nome);
            item.baixarEstoque(1);
            item.preparar();
            pedido.adicionarItem(item);
        }

        double total = pedido.calcularTotal();
        cliente.pagar(total);
        caixa.registrarVenda("Pedido de " + cliente.getNome(), total);

        return pedido;
    }
}
