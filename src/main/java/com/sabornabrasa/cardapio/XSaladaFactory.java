package com.sabornabrasa.cardapio;

public class XSaladaFactory extends LancheFactory {
    @Override
    public Produto criarLanche() {
        Ingrediente salada = IngredienteCache.getIngrediente("Alface", true);
        return new Produto("X-Salada", 25.0, salada);
    }
}
