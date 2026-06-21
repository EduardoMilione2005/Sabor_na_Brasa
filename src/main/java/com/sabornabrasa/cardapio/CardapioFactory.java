package com.sabornabrasa.cardapio;

public interface CardapioFactory {
    Hamburguer criarHamburguer();
    Produto criarBebida();
    Produto criarBatata();
}
