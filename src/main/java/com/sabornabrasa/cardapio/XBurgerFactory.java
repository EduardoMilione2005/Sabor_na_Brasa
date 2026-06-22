package com.sabornabrasa.cardapio;

public class XBurgerFactory extends LancheFactory {
    @Override
    public Produto criarLanche() {
        Ingrediente carne = IngredienteCache.getIngrediente("Carne bovina", false);
        return new Produto("X-Burger", 28.0, carne);
    }
}
