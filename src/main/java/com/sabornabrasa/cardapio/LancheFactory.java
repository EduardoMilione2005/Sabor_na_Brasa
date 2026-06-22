package com.sabornabrasa.cardapio;

public abstract class LancheFactory {
    public abstract Produto criarLanche();
    public final Produto prepararEExibir() {
        return criarLanche();
    }
}
