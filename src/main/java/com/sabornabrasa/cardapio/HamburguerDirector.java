package com.sabornabrasa.cardapio;

public class HamburguerDirector {
    public Hamburguer criarTradicional() {
        return new HamburguerBuilder()
                .nome("X-Burger Tradicional")
                .pao("Brioche")
                .carne("Angus 180g")
                .queijo("Cheddar")
                .comBacon()
                .molho("Especial")
                .preco(32.0)
                .build();
    }
    public Hamburguer criarVegano() {
        return new HamburguerBuilder()
                .nome("X-Burger Vegano")
                .pao("Integral")
                .carne("Hamburguer de grão-de-bico")
                .queijo("Queijo vegano")
                .comSalada()
                .molho("Tahine")
                .preco(35.0)
                .extraIngrediente("Alface", true)
                .build();
    }
    public Hamburguer criarPremium() {
        return new HamburguerBuilder()
                .nome("X-Premium")
                .pao("Brioche Black")
                .carne("Wagyu 200g")
                .queijo("Gruyère")
                .comBacon()
                .comSalada()
                .molho("Trufado")
                .preco(58.0)
                .build();
    }
}
