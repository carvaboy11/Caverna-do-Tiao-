package com.taverna.repository;

import java.util.ArrayList;
import java.util.List;
import com.taverna.model.Item;
import com.taverna.exception.ItemIndisponivelException;

public class EstoqueRepository implements ItemRepository {

    private final List<Item> itens = new ArrayList<>();

    @Override
    public List<Item> listarTodos() {
        return itens;
    }

    @Override
    public Item buscarPorNome(String nome) throws ItemIndisponivelException {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        throw new ItemIndisponivelException("Item \"" + nome + "\" não existe no cardápio.");
    }

    @Override
    public void adicionar(Item item) {
        itens.add(item);
    }
}
