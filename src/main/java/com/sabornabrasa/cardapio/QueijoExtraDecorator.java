package com.sabornabrasa.cardapio;

public class QueijoExtraDecorator extends HamburguerDecorator {
    public QueijoExtraDecorator(ItemCardapio decorado) { super(decorado); }
    @Override public String getNome()  { return decorado.getNome() + " + Queijo Extra"; }
    @Override public double getPreco() { return decorado.getPreco() + 3.0; }
    @Override public void exibir(String prefixo) {}
}
