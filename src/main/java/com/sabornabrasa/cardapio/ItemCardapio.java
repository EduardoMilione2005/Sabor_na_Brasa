package com.sabornabrasa.cardapio;

import com.sabornabrasa.relatorio.VisitorRelatorio;
public interface ItemCardapio {
    String getNome();
    double getPreco();
    void exibir(String prefixo);
    void aceitar(VisitorRelatorio visitor);
}
