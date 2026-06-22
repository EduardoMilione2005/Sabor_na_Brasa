package com.sabornabrasa.cardapio;

import com.sabornabrasa.relatorio.VisitorRelatorio;
public class Produto implements ItemCardapio {
    private final String nome;
    private final double preco;
    private final Ingrediente ingredientePrincipal;
    public Produto(String nome, double preco, Ingrediente ingredientePrincipal) {
        this.nome = nome;
        this.preco = preco;
        this.ingredientePrincipal = ingredientePrincipal;
    }
    @Override public String getNome()  { return nome; }
    @Override public double getPreco() { return preco; }
    @Override public void exibir(String prefixo) {}
    public boolean isVegano() { return ingredientePrincipal.isVegano(); }
    @Override
    public void aceitar(VisitorRelatorio visitor) { visitor.visitarProduto(this); }
    @Override
    public String toString() { return nome + " (R$ " + preco + ")"; }
}
