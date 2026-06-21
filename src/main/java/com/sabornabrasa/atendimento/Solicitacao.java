package com.sabornabrasa.atendimento;

public class Solicitacao {
    public enum Tipo { PEDIDO_SIMPLES, RECLAMACAO, PROBLEMA_GRAVE, REEMBOLSO }
    private final Tipo tipo;
    private final String descricao;
    private final String nomeCliente;
    private final String pedido;
    public Solicitacao(Tipo tipo, String descricao, String nomeCliente, String pedido) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.nomeCliente = nomeCliente;
        this.pedido = pedido;
    }
    public Tipo getTipo()          { return tipo; }
    public String getDescricao()   { return descricao; }
    public String getNomeCliente() { return nomeCliente; }
    public String getPedido()      { return pedido; }
    @Override
    public String toString() {
        return "Solicitacao{tipo=" + tipo + ", cliente='" + nomeCliente + "'}";
    }
}
