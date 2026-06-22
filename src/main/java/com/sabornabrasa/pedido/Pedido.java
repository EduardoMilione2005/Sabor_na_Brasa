package com.sabornabrasa.pedido;

import com.sabornabrasa.cardapio.ItemCardapio;
import java.util.ArrayList;
import java.util.List;
public class Pedido {
    private static int contadorGlobal = 0;
    private final int numero;
    private final String nomeCliente;
    private final List<ItemCardapio> itens = new ArrayList<>();
    private EstadoPedido estado;
    private double desconto;
    private final List<ObservadorPedido> observadores = new ArrayList<>();
    public Pedido(String nomeCliente) {
        this.nomeCliente = nomeCliente;
        this.numero = ++contadorGlobal;
        this.estado = new EstadoRecebido();
    }
    public void adicionarItem(ItemCardapio item) { itens.add(item); }
    public List<ItemCardapio> getItens() { return List.copyOf(itens); }
    public double getTotal() {
        double bruto = itens.stream().mapToDouble(ItemCardapio::getPreco).sum();
        return bruto - desconto;
    }
    public void setDesconto(double desconto) { this.desconto = desconto; }
    public void avancarEstado() {
        estado = estado.avancar(this);
        notificarObservadores();
    }
    public void cancelar() {
        estado = estado.cancelar(this);
        notificarObservadores();
    }
    public String getStatusDescricao() { return estado.descricao(); }
    public void assinar(ObservadorPedido obs)            { observadores.add(obs); }
    public void cancelarAssinatura(ObservadorPedido obs) { observadores.remove(obs); }
    private void notificarObservadores() {
        for (ObservadorPedido obs : observadores) obs.atualizar(estado.descricao(), numero);
    }
    public PedidoMemento salvarEstado() {
        return new PedidoMemento(estado.descricao(), getTotal(), nomeCliente);
    }
    public void restaurarEstado(PedidoMemento memento) {
    }
    void setEstado(EstadoPedido estado) { this.estado = estado; }
    public void setEstadoPorNome(String nome) {
        switch (nome) {
            case "Recebido"   -> this.estado = new EstadoRecebido();
            case "Preparando" -> this.estado = new EstadoPreparando();
            case "Pronto"     -> this.estado = new EstadoPronto();
            case "Entregue"   -> this.estado = new EstadoEntregue();
            case "Cancelado"  -> this.estado = new EstadoCancelado();
        }
    }
    public int getNumero()         { return numero; }
    public String getNomeCliente() { return nomeCliente; }
    @Override
    public String toString() {
        return "Pedido #" + numero + " | " + nomeCliente
                + " | " + getStatusDescricao()
                + " | R$ " + String.format("%.2f", getTotal());
    }
}
