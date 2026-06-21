package com.sabornabrasa.atendimento;

import com.sabornabrasa.pedido.Pedido;
public interface MediadorAtendimento {
    void clienteFezPedido(String nomeCliente, Pedido pedido);
    void garcomRegistrouPedido(Pedido pedido);
    void cozinhaFinalizouPedido(Pedido pedido);
    void notificarCliente(String nomeCliente, String mensagem);
}
