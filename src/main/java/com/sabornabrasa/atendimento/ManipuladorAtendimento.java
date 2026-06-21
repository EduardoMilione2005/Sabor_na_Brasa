package com.sabornabrasa.atendimento;

public abstract class ManipuladorAtendimento {
    private ManipuladorAtendimento proximo;
    public ManipuladorAtendimento setProximo(ManipuladorAtendimento proximo) {
        this.proximo = proximo;
        return proximo;
    }
    public final void atender(Solicitacao solicitacao) {
        if (podeAtender(solicitacao)) {
            processar(solicitacao);
        } else if (proximo != null) {
            proximo.atender(solicitacao);
        }
    }
    protected abstract boolean podeAtender(Solicitacao solicitacao);
    protected abstract void processar(Solicitacao solicitacao);
    protected abstract String nome();
}
