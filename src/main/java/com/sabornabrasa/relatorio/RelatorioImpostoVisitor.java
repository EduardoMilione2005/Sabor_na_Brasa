package com.sabornabrasa.relatorio;

import com.sabornabrasa.cardapio.Combo;
import com.sabornabrasa.cardapio.Hamburguer;
import com.sabornabrasa.cardapio.Produto;
public class RelatorioImpostoVisitor implements VisitorRelatorio {
    private static final double TAXA = 0.12;
    private double totalImposto = 0;
    @Override public void visitarProduto(Produto p)   { totalImposto += p.getPreco() * TAXA; }
    @Override public void visitarHamburguer(Hamburguer h) { totalImposto += h.getPreco() * TAXA; }
    @Override public void visitarCombo(Combo combo)   {}
    public double getTotalImposto() { return totalImposto; }
}
