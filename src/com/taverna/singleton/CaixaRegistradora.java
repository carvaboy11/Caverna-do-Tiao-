package com.taverna.singleton;

import java.util.ArrayList;
import java.util.List;

public class CaixaRegistradora {

    private static CaixaRegistradora instancia;

    private double totalArrecadado;
    private final List<String> historicoVendas;

    private CaixaRegistradora() {
        this.totalArrecadado = 0.0;
        this.historicoVendas = new ArrayList<>();
    }

    public static synchronized CaixaRegistradora getInstance() {
        if (instancia == null) {
            instancia = new CaixaRegistradora();
        }
        return instancia;
    }

    public void registrarVenda(String descricao, double valor) {
        totalArrecadado += valor;
        historicoVendas.add(descricao + " -> R$ " + String.format("%.2f", valor));
    }

    public double getTotalArrecadado() {
        return totalArrecadado;
    }

    public List<String> getHistoricoVendas() {
        return historicoVendas;
    }
}
