package com.sabornabrasa.cardapio;

import com.sabornabrasa.relatorio.VisitorRelatorio;
public abstract class HamburguerDecorator implements ItemCardapio {
    protected final ItemCardapio decorado;
    protected HamburguerDecorator(ItemCardapio decorado) {
        this.decorado = decorado;
    }
    @Override
    public String getNome()  { return decorado.getNome(); }
    @Override
    public double getPreco() { return decorado.getPreco(); }
    @Override
    public void exibir(String prefixo) { decorado.exibir(prefixo); }
    @Override
    public void aceitar(VisitorRelatorio visitor) {
        Ingrediente i = IngredienteCache.getIngrediente("Misto", false);
        visitor.visitarProduto(new Produto(getNome(), getPreco(), i));
    }
}
