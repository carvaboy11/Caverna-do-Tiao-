package com.taverna.model;

import com.taverna.exception.SaldoInsuficienteException;

public class Cliente {

    private String nome;
    private double saldo;

    public Cliente(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void adicionarSaldo(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor a adicionar deve ser maior que zero.");
        }
        this.saldo += valor;
    }

    public void pagar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException(
                nome + " não tem ouro suficiente. Saldo: " + saldo + ", necessário: " + valor);
        }
        this.saldo -= valor;
    }

    @Override
    public String toString() {
        return nome + " (saldo: " + saldo + " moedas)";
    }
}
