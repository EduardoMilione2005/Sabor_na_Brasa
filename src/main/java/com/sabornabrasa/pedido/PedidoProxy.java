package com.sabornabrasa.pedido;

public class PedidoProxy {
    private final FilaComandos filaComandos;
    private final HistoricoPedido historico;
    public PedidoProxy(FilaComandos filaComandos, HistoricoPedido historico) {
        this.filaComandos = filaComandos;
        this.historico    = historico;
    }
    public void processarAvancar(Pedido pedido) {
        if (pedido.getItens().isEmpty()) return;
        historico.salvar(pedido.salvarEstado());
        filaComandos.executar(new AvancarPedidoCommand(pedido));
    }
    public void processarCancelamento(Pedido pedido) {
        historico.salvar(pedido.salvarEstado());
        pedido.cancelar();
    }
}
