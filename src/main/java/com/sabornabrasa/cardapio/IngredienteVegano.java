package com.sabornabrasa.cardapio;

public class IngredienteVegano implements Ingrediente {
    private final String nome;
    public IngredienteVegano(String nome) {
        this.nome = nome;
    }
    @Override
    public String getNome() { return nome; }
    @Override
    public String getDescricao() {
        return "Ingrediente vegano: " + nome;
    }
    @Override
    public boolean isVegano() { return true; }
}
