package com.sabornabrasa.pedido;

import java.util.ArrayDeque;
import java.util.Deque;
public class FilaComandos {
    private final Deque<ComandoPedido> executados = new ArrayDeque<>();
    public void executar(ComandoPedido comando) {
        comando.executar();
        executados.push(comando);
    }
    public void desfazerUltimo() {
        if (executados.isEmpty()) return;
        ComandoPedido ultimo = executados.pop();
        ultimo.desfazer();
    }
}
