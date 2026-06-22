package com.sabornabrasa.pedido;

public interface ComandoPedido {
    void executar();
    void desfazer();
    String descricao();
}
