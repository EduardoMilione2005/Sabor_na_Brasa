package com.sabornabrasa.pagamento;

public interface DescontoStrategy {
    double calcularDesconto(double valorBruto);
    String descricao();
}
