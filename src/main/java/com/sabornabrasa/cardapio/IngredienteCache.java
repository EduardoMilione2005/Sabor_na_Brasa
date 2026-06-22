package com.sabornabrasa.cardapio;

import java.util.HashMap;
import java.util.Map;
public class IngredienteCache {
    private static final Map<String, Ingrediente> cache = new HashMap<>();
    private IngredienteCache() {}
    public static Ingrediente getIngrediente(String nome, boolean vegano) {
        String chave = nome + (vegano ? "_vegano" : "_tradicional");
        return cache.computeIfAbsent(chave, k ->
                vegano
                        ? new IngredienteVegano(nome)
                        : new IngredienteTradicional(nome));
    }
    public static int totalInstancias() {
        return cache.size();
    }
}
