package com.sabornabrasa.pagamento;

public class SemDesconto implements DescontoStrategy {
    @Override public double calcularDesconto(double v) { return 0; }
    @Override public String descricao() { return "Sem desconto"; }
}
