package com.sabornabrasa.cozinha;

import com.sabornabrasa.pedido.Pedido;
public abstract class PreparoPedido {
    public final void preparar(Pedido pedido) {
        separarIngredientes(pedido);
        grelharCarne(pedido);
        montarLanche(pedido);
        finalizarApresentacao(pedido);
    }
    protected abstract void separarIngredientes(Pedido pedido);
    protected abstract void grelharCarne(Pedido pedido);
    protected void montarLanche(Pedido pedido)          {}
    protected void finalizarApresentacao(Pedido pedido) {}
}
