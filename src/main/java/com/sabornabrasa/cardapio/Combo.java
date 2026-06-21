package com.sabornabrasa.cardapio;

import com.sabornabrasa.relatorio.VisitorRelatorio;
import java.util.ArrayList;
import java.util.List;
public class Combo implements ItemCardapio {
    private final String nome;
    private final List<ItemCardapio> itens = new ArrayList<>();
    public Combo(String nome) { this.nome = nome; }
    public void adicionar(ItemCardapio item) { itens.add(item); }
    public void remover(ItemCardapio item)   { itens.remove(item); }
    public List<ItemCardapio> getItens()     { return List.copyOf(itens); }
    @Override public String getNome()  { return nome; }
    @Override public double getPreco() {
        return itens.stream().mapToDouble(ItemCardapio::getPreco).sum();
    }
    @Override public void exibir(String prefixo) {}
    @Override
    public void aceitar(VisitorRelatorio visitor) {
        visitor.visitarCombo(this);
        for (ItemCardapio item : itens) { item.aceitar(visitor); }
    }
}
