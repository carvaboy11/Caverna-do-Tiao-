package com.taverna.model;

import com.taverna.interfaces.Preparavel;

public class Comida extends Item implements Preparavel {

    private int tempoPreparoMinutos;

    public Comida(String nome, double preco, int quantidadeEstoque, int tempoPreparoMinutos) {
        super(nome, preco, quantidadeEstoque);
        this.tempoPreparoMinutos = tempoPreparoMinutos;
    }

    public int getTempoPreparoMinutos() {
        return tempoPreparoMinutos;
    }

    @Override
    public String getCategoria() {
        return "Comida";
    }

    @Override
    public void preparar() {
        System.out.println("Preparando " + nome + " na fogueira (" + tempoPreparoMinutos + " min)...");
    }
}
