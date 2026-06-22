package com.sabornabrasa.cardapio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Menu implements Iterable<ItemCardapio> {
    private final String categoria;
    private final List<ItemCardapio> itens = new ArrayList<>();
    public Menu(String categoria) { this.categoria = categoria; }
    public void adicionar(ItemCardapio item) { itens.add(item); }
    public String getCategoria() { return categoria; }
    @Override public Iterator<ItemCardapio> iterator() { return itens.iterator(); }
    public void exibirTodos() {}
    public void listar()      {}
}
