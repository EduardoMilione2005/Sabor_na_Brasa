package com.sabornabrasa.atendimento;

public class Gerente extends ManipuladorAtendimento {
    @Override
    protected boolean podeAtender(Solicitacao s) {
        return s.getTipo() == Solicitacao.Tipo.RECLAMACAO
                || s.getTipo() == Solicitacao.Tipo.REEMBOLSO;
    }
    @Override
    protected void processar(Solicitacao s) {}
    @Override
    protected String nome() { return "Gerente"; }
}
