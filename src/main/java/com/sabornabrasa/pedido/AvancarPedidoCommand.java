package com.sabornabrasa.pedido;

public class AvancarPedidoCommand implements ComandoPedido {
    private final Pedido pedido;
    private String estadoAnterior;
    public AvancarPedidoCommand(Pedido pedido) { this.pedido = pedido; }
    @Override
    public void executar() {
        estadoAnterior = pedido.getStatusDescricao();
        pedido.avancarEstado();
    }
    @Override
    public void desfazer() {
        if (estadoAnterior != null) {
            pedido.setEstadoPorNome(estadoAnterior);
        }
    }
    @Override
    public String descricao() {
        return "Avançar estado do pedido #" + pedido.getNumero();
    }
}
