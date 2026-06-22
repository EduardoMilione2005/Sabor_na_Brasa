package com.sabornabrasa.pedido;

public interface EstadoPedido {
    String descricao();
    EstadoPedido avancar(Pedido pedido);
    EstadoPedido cancelar(Pedido pedido);
}
