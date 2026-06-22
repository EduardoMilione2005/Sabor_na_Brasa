package com.sabornabrasa.pagamento;

import com.sabornabrasa.pedido.Pedido;
public class ProcessadorPagamento {
    private DescontoStrategy estrategiaDesconto;
    public ProcessadorPagamento() {
        this.estrategiaDesconto = new SemDesconto();
    }
    public void setEstrategiaDesconto(DescontoStrategy estrategia) {
        this.estrategiaDesconto = estrategia;
    }
    public double processar(Pedido pedido) {
        double bruto    = pedido.getTotal();
        double desconto = estrategiaDesconto.calcularDesconto(bruto);
        pedido.setDesconto(desconto);
        return bruto - desconto;
    }
    public String getEstrategiaDescricao() { return estrategiaDesconto.descricao(); }
}
