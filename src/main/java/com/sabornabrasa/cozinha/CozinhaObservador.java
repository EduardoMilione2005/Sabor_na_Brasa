package com.sabornabrasa.cozinha;

import com.sabornabrasa.pedido.ObservadorPedido;
public class CozinhaObservador implements ObservadorPedido {
    private final PreparoPedido estrategiaPrep;
    public CozinhaObservador(PreparoPedido estrategiaPrep) {
        this.estrategiaPrep = estrategiaPrep;
    }
    @Override
    public void atualizar(String status, int numeroPedido) {
    }
    public PreparoPedido getEstrategiaPrep() { return estrategiaPrep; }
}
