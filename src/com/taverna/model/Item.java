package com.taverna.model;

import com.taverna.exception.ItemIndisponivelException;
import com.taverna.interfaces.Preparavel;

public abstract class Item implements Preparavel {

    protected String nome;
    protected double preco;
    protected int quantidadeEstoque;

    public Item(String nome, double preco, int quantidadeEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public abstract String getCategoria();

    public void baixarEstoque(int quantidade) throws ItemIndisponivelException {
        if (quantidade > this.quantidadeEstoque) {
            throw new ItemIndisponivelException(
                "Estoque insuficiente para \"" + nome + "\". Disponível: " + quantidadeEstoque);
        }
        this.quantidadeEstoque -= quantidade;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (estoque: %d)",
            getCategoria(), nome, preco, quantidadeEstoque);
    }
}
