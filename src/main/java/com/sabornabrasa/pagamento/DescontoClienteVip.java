package com.sabornabrasa.pagamento;

public class DescontoClienteVip implements DescontoStrategy {
    @Override
    public double calcularDesconto(double valorBruto) {
        return valorBruto * 0.20;
    }
    @Override
    public String descricao() { return "Desconto cliente VIP (20%)"; }
}
