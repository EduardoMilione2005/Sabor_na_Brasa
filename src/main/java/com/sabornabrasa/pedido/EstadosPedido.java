package com.sabornabrasa.pedido;

class EstadoRecebido implements EstadoPedido {
    @Override public String descricao() { return "Recebido"; }
    @Override
    public EstadoPedido avancar(Pedido pedido) {
        return new EstadoPreparando();
    }
    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        return new EstadoCancelado();
    }
}
class EstadoPreparando implements EstadoPedido {
    @Override public String descricao() { return "Preparando"; }
    @Override
    public EstadoPedido avancar(Pedido pedido) {
        return new EstadoPronto();
    }
    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        return this;
    }
}
class EstadoPronto implements EstadoPedido {
    @Override public String descricao() { return "Pronto"; }
    @Override
    public EstadoPedido avancar(Pedido pedido) {
        return new EstadoEntregue();
    }
    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        return this;
    }
}
class EstadoEntregue implements EstadoPedido {
    @Override public String descricao() { return "Entregue"; }
    @Override
    public EstadoPedido avancar(Pedido pedido) {
        return this;
    }
    @Override
    public EstadoPedido cancelar(Pedido pedido) {
        return this;
    }
}
class EstadoCancelado implements EstadoPedido {
    @Override public String descricao() { return "Cancelado"; }
    @Override
    public EstadoPedido avancar(Pedido pedido) {
        return this;
    }
    @Override
    public EstadoPedido cancelar(Pedido pedido) { return this; }
}
