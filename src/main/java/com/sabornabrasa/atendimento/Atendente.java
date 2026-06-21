package com.sabornabrasa.atendimento;

public class Atendente extends ManipuladorAtendimento {
    @Override
    protected boolean podeAtender(Solicitacao s) {
        return s.getTipo() == Solicitacao.Tipo.PEDIDO_SIMPLES;
    }
    @Override
    protected void processar(Solicitacao s) {}
    @Override
    protected String nome() { return "Atendente"; }
}
