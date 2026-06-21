package com.sabornabrasa.atendimento;

import com.sabornabrasa.pedido.Pedido;
public class CentralAtendimento implements MediadorAtendimento {
    @Override
    public void clienteFezPedido(String nomeCliente, Pedido pedido) {
        garcomRegistrouPedido(pedido);
    }
    @Override
    public void garcomRegistrouPedido(Pedido pedido) {
        pedido.avancarEstado();
    }
    @Override
    public void cozinhaFinalizouPedido(Pedido pedido) {
        pedido.avancarEstado();
        notificarCliente(pedido.getNomeCliente(), "Pedido pronto");
    }
    @Override
    public void notificarCliente(String nomeCliente, String mensagem) {}
}
