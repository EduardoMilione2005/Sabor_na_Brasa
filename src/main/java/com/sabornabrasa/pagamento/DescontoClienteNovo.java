package com.sabornabrasa.pagamento;

public class DescontoClienteNovo implements DescontoStrategy {
    @Override
    public double calcularDesconto(double valorBruto) {
        return valorBruto * 0.10;
    }
    @Override
    public String descricao() { return "Desconto cliente novo (10%)"; }
}
