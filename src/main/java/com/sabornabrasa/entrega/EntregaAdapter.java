package com.sabornabrasa.entrega;

import com.sabornabrasa.pedido.Pedido;
public class EntregaAdapter implements ServicoEntrega {
    private final SistemaEntregaLegado legado;
    public EntregaAdapter(SistemaEntregaLegado legado) {
        this.legado = legado;
    }
    @Override
    public void realizarEntrega(Pedido pedido, String enderecoCliente) {
        String produto = "Pedido #" + pedido.getNumero() + " (" + pedido.getNomeCliente() + ")";
        legado.despacharMercadoria(enderecoCliente, produto);
        pedido.avancarEstado();
    }
    @Override
    public String rastrearPedido(int numeroPedido) {
        return legado.rastrearCodigo("SNB-" + numeroPedido);
    }
}
