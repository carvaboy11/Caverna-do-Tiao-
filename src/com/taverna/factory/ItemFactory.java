package com.taverna.factory;

import com.taverna.model.Bebida;
import com.taverna.model.Comida;
import com.taverna.model.Item;

public class ItemFactory {

    public static Item criarBebida(String nome, double preco, int estoque, boolean alcoolica) {
        return new Bebida(nome, preco, estoque, alcoolica);
    }

    public static Item criarComida(String nome, double preco, int estoque, int tempoPreparoMinutos) {
        return new Comida(nome, preco, estoque, tempoPreparoMinutos);
    }

    public static Item criarItem(String tipo, String nome, double preco, int estoque) {
        switch (tipo.toUpperCase()) {
            case "BEBIDA":
                return criarBebida(nome, preco, estoque, true);
            case "COMIDA":
                return criarComida(nome, preco, estoque, 10);
            default:
                throw new IllegalArgumentException("Tipo de item desconhecido: " + tipo);
        }
    }
}
