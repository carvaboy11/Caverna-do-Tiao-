package com.taverna.repository;

import java.util.List;
import com.taverna.model.Item;
import com.taverna.exception.ItemIndisponivelException;

public interface ItemRepository {

    List<Item> listarTodos();

    Item buscarPorNome(String nome) throws ItemIndisponivelException;

    void adicionar(Item item);
}
