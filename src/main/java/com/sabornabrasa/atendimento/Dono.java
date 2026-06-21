package com.sabornabrasa.atendimento;

public class Dono extends ManipuladorAtendimento {
    @Override protected boolean podeAtender(Solicitacao s) { return true; }
    @Override protected void processar(Solicitacao s) {}
    @Override protected String nome() { return "Dono"; }
}
