package com.sabornabrasa.relatorio;

import com.sabornabrasa.cardapio.Combo;
import com.sabornabrasa.cardapio.Hamburguer;
import com.sabornabrasa.cardapio.Produto;
public interface VisitorRelatorio {
    void visitarProduto(Produto produto);
    void visitarHamburguer(Hamburguer hamburguer);
    void visitarCombo(Combo combo);
}
