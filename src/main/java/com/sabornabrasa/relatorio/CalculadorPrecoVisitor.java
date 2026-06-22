package com.sabornabrasa.relatorio;

import com.sabornabrasa.cardapio.Combo;
import com.sabornabrasa.cardapio.Hamburguer;
import com.sabornabrasa.cardapio.Produto;
public class CalculadorPrecoVisitor implements VisitorRelatorio {
    private double total = 0;
    @Override public void visitarProduto(Produto produto)       { total += produto.getPreco(); }
    @Override public void visitarHamburguer(Hamburguer h)       { total += h.getPreco(); }
    @Override public void visitarCombo(Combo combo)             {}
    public double getTotal() { return total; }
    public void resetar()    { total = 0; }
}
