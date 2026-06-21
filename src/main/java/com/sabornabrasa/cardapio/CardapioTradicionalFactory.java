package com.sabornabrasa.cardapio;

public class CardapioTradicionalFactory implements CardapioFactory {
    private final HamburguerDirector director = new HamburguerDirector();
    @Override
    public Hamburguer criarHamburguer() {
        return director.criarTradicional();
    }
    @Override
    public Produto criarBebida() {
        Ingrediente agua = IngredienteCache.getIngrediente("Água", false);
        return new Produto("Refrigerante Tradicional", 8.0, agua);
    }
    @Override
    public Produto criarBatata() {
        Ingrediente batata = IngredienteCache.getIngrediente("Batata", false);
        return new Produto("Batata Frita Tradicional", 12.0, batata);
    }
}
