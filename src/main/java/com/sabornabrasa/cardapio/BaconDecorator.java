package com.sabornabrasa.cardapio;

public class BaconDecorator extends HamburguerDecorator {
    public BaconDecorator(ItemCardapio decorado) { super(decorado); }
    @Override public String getNome()  { return decorado.getNome() + " + Bacon"; }
    @Override public double getPreco() { return decorado.getPreco() + 5.0; }
    @Override public void exibir(String prefixo) {}
}
