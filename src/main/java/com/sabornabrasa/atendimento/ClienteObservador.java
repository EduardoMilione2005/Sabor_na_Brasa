package com.sabornabrasa.atendimento;

import com.sabornabrasa.pedido.ObservadorPedido;
public class ClienteObservador implements ObservadorPedido {
    private final String nome;
    public ClienteObservador(String nome) { this.nome = nome; }
    @Override public void atualizar(String status, int numeroPedido) {}
    public String getNome() { return nome; }
}
