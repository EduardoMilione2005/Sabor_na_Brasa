package com.sabornabrasa.pedido;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
public class HistoricoPedido {
    private final Deque<PedidoMemento> historico = new ArrayDeque<>();
    public void salvar(PedidoMemento memento) {
        historico.push(memento);
    }
    public PedidoMemento desfazer() {
        if (historico.isEmpty()) return null;
        return historico.pop();
    }
    public List<PedidoMemento> listarTodos() { return new ArrayList<>(historico); }
    public int tamanho() { return historico.size(); }
}
