package com.taverna.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private Cliente cliente;
    private List<Item> itens;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Item item : itens) {
            total += item.getPreco();
        }
        return total;
    }
}
