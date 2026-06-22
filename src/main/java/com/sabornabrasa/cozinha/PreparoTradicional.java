package com.sabornabrasa.cozinha;

import com.sabornabrasa.pedido.Pedido;
public class PreparoTradicional extends PreparoPedido {
    @Override protected void separarIngredientes(Pedido pedido) {}
    @Override protected void grelharCarne(Pedido pedido)        {}
    @Override protected void finalizarApresentacao(Pedido pedido) {}
}
