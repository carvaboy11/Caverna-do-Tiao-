package com.taverna.model;

import com.taverna.interfaces.Preparavel;

public class Bebida extends Item implements Preparavel {

    private boolean alcoolica;

    public Bebida(String nome, double preco, int quantidadeEstoque, boolean alcoolica) {
        super(nome, preco, quantidadeEstoque);
        this.alcoolica = alcoolica;
    }

    public boolean isAlcoolica() {
        return alcoolica;
    }

    @Override
    public String getCategoria() {
        return "Bebida";
    }

    @Override
    public void preparar() {
        System.out.println("Servindo " + nome + " em uma caneca de barro...");
    }
}
