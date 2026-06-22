package com.sabornabrasa.cardapio;

public class IngredienteTradicional implements Ingrediente {
    private final String nome;
    public IngredienteTradicional(String nome) {
        this.nome = nome;
    }
    @Override
    public String getNome() { return nome; }
    @Override
    public String getDescricao() {
        return "Ingrediente tradicional: " + nome;
    }
    @Override
    public boolean isVegano() { return false; }
}
