package com.sabornabrasa.cardapio;

public class HamburguerBuilder {
    private final Hamburguer hamburguer = new Hamburguer();
    public HamburguerBuilder nome(String nome) {
        hamburguer.setNome(nome);
        return this;
    }
    public HamburguerBuilder pao(String pao) {
        hamburguer.setPao(pao);
        return this;
    }
    public HamburguerBuilder carne(String carne) {
        hamburguer.setCarne(carne);
        return this;
    }
    public HamburguerBuilder queijo(String queijo) {
        hamburguer.setQueijo(queijo);
        return this;
    }
    public HamburguerBuilder comBacon() {
        hamburguer.setBacon(true);
        return this;
    }
    public HamburguerBuilder comSalada() {
        hamburguer.setSalada(true);
        return this;
    }
    public HamburguerBuilder molho(String molho) {
        hamburguer.setMolho(molho);
        return this;
    }
    public HamburguerBuilder preco(double preco) {
        hamburguer.setPreco(preco);
        return this;
    }
    public HamburguerBuilder extraIngrediente(String nome, boolean vegano) {
        hamburguer.adicionarExtra(IngredienteCache.getIngrediente(nome, vegano));
        return this;
    }
    public Hamburguer build() {
        return hamburguer;
    }
}
