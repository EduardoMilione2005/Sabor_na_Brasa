package com.sabornabrasa.pedido;

public class PedidoMemento {
    private final String estado;
    private final double totalNaMomento;
    private final String nomeCliente;
    private final long timestamp;
    public PedidoMemento(String estado, double totalNaMomento, String nomeCliente) {
        this.estado = estado;
        this.totalNaMomento = totalNaMomento;
        this.nomeCliente = nomeCliente;
        this.timestamp = System.currentTimeMillis();
    }
    public String getEstado()         { return estado; }
    public double getTotalNaMomento() { return totalNaMomento; }
    public String getNomeCliente()    { return nomeCliente; }
    public long getTimestamp()        { return timestamp; }
    @Override
    public String toString() {
        return "Memento{estado='" + estado + "', total=R$"
                + String.format("%.2f", totalNaMomento) + '}';
    }
}
