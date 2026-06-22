package com.sabornabrasa.entrega;

import com.sabornabrasa.pedido.Pedido;
public interface ServicoEntrega {
    void realizarEntrega(Pedido pedido, String enderecoCliente);
    String rastrearPedido(int numeroPedido);
}
