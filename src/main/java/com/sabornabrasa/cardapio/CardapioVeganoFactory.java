package com.sabornabrasa.cardapio;

public class CardapioVeganoFactory implements CardapioFactory {
    private final HamburguerDirector director = new HamburguerDirector();
    @Override
    public Hamburguer criarHamburguer() {
        return director.criarVegano();
    }
    @Override
    public Produto criarBebida() {
        Ingrediente suco = IngredienteCache.getIngrediente("Fruta", true);
        return new Produto("Suco Natural Vegano", 10.0, suco);
    }
    @Override
    public Produto criarBatata() {
        Ingrediente batata = IngredienteCache.getIngrediente("Batata doce", true);
        return new Produto("Batata Doce Vegana", 14.0, batata);
    }
}
