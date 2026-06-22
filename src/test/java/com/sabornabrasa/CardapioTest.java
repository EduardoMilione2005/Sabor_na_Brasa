package com.sabornabrasa;
import com.sabornabrasa.cardapio.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardapioTest {
    private static final String INGREDIENTE_QUEIJO  = "Queijo";
    private static final String INGREDIENTE_BATATA  = "Batata";
    private static final String INGREDIENTE_PAO     = "Pao";
    private static final double PRECO_TRADICIONAL   = 32.0;
    private static final double PRECO_XBURGER       = 28.0;
    private static final double PRECO_BACON         = 5.0;
    private static final double PRECO_QUEIJO_EXTRA  = 3.0;
    private static final double PRECO_ITEM_A        = 10.0;
    private static final double PRECO_ITEM_B        = 20.0;
    private static final double PRECO_COMBO_VAZIO   = 0.0;
    @Test
    void mesmaChamadaRetornaMesmaInstancia() {
        Ingrediente a = IngredienteCache.getIngrediente(INGREDIENTE_QUEIJO, false);
        Ingrediente b = IngredienteCache.getIngrediente(INGREDIENTE_QUEIJO, false);
        assertSame(a, b);
    }
    @Test
    void versaoVeganaETradicionalSaoInstanciasDiferentes() {
        Ingrediente tradicional = IngredienteCache.getIngrediente(INGREDIENTE_BATATA, false);
        Ingrediente vegano      = IngredienteCache.getIngrediente(INGREDIENTE_BATATA, true);
        assertNotSame(tradicional, vegano);
    }
    @Test
    void ingredienteTradicionalNaoEVegano() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_QUEIJO);
        assertFalse(i.isVegano());
    }
    @Test
    void ingredienteVeganoEVegano() {
        Ingrediente i = new IngredienteVegano(INGREDIENTE_QUEIJO);
        assertTrue(i.isVegano());
    }
    @Test
    void ingredienteTradicionalRetornaNomeCorreto() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_QUEIJO);
        assertEquals(INGREDIENTE_QUEIJO, i.getNome());
    }
    @Test
    void hamburguerTradicionalTemNomeCorreto() {
        Hamburguer h = new HamburguerDirector().criarTradicional();
        assertEquals("X-Burger Tradicional", h.getNome());
    }
    @Test
    void hamburguerTradicionalTemPrecoCorreto() {
        Hamburguer h = new HamburguerDirector().criarTradicional();
        assertEquals(PRECO_TRADICIONAL, h.getPreco());
    }
    @Test
    void hamburguerTradicionalTemBacon() {
        Hamburguer h = new HamburguerDirector().criarTradicional();
        assertTrue(h.isBacon());
    }
    @Test
    void hamburguerVeganoTemSalada() {
        Hamburguer h = new HamburguerDirector().criarVegano();
        assertTrue(h.isSalada());
    }
    @Test
    void hamburguerVeganoNaoTemBacon() {
        Hamburguer h = new HamburguerDirector().criarVegano();
        assertFalse(h.isBacon());
    }
    @Test
    void cloneEObjetoDiferente() {
        Hamburguer original = new HamburguerDirector().criarTradicional();
        Hamburguer clone    = original.clone();
        assertNotSame(original, clone);
    }
    @Test
    void alterarCloneNaoAlteraOriginal() {
        Hamburguer original = new HamburguerDirector().criarTradicional();
        Hamburguer clone    = original.clone();
        clone.setNome("Especial");
        assertEquals("X-Burger Tradicional", original.getNome());
    }
    @Test
    void xBurgerFactoryCriaProdutoComNomeCorreto() {
        Produto p = new XBurgerFactory().criarLanche();
        assertEquals("X-Burger", p.getNome());
    }
    @Test
    void xBurgerFactoryCriaProdutoComPrecoCorreto() {
        Produto p = new XBurgerFactory().criarLanche();
        assertEquals(PRECO_XBURGER, p.getPreco());
    }
    @Test
    void xSaladaFactoryCriaProdutoComNomeCorreto() {
        Produto p = new XSaladaFactory().criarLanche();
        assertEquals("X-Salada", p.getNome());
    }
    @Test
    void familiaTradicionalCriaHamburguerSemVeganoNoNome() {
        CardapioFactory f = new CardapioTradicionalFactory();
        assertFalse(f.criarHamburguer().getNome().contains("Vegano"));
    }
    @Test
    void familiaVeganaCriaHamburguerComVeganoNoNome() {
        CardapioFactory f = new CardapioVeganoFactory();
        assertTrue(f.criarHamburguer().getNome().contains("Vegano"));
    }
    @Test
    void familiaVeganaCriaBebidaComVeganoNoNome() {
        CardapioFactory f = new CardapioVeganoFactory();
        assertTrue(f.criarBebida().getNome().contains("Vegano"));
    }
    @Test
    void familiasNaoCompartilhamHamburguer() {
        CardapioFactory trad = new CardapioTradicionalFactory();
        CardapioFactory veg  = new CardapioVeganoFactory();
        assertNotEquals(trad.criarHamburguer().getNome(), veg.criarHamburguer().getNome());
    }
    @Test
    void baconDecoratorAumentaPreco() {
        ItemCardapio base = new HamburguerDirector().criarTradicional();
        ItemCardapio com  = new BaconDecorator(base);
        assertEquals(PRECO_TRADICIONAL + PRECO_BACON, com.getPreco());
    }
    @Test
    void baconDecoratorAdicionaNomeAoItem() {
        ItemCardapio base = new HamburguerDirector().criarTradicional();
        ItemCardapio com  = new BaconDecorator(base);
        assertTrue(com.getNome().contains("Bacon"));
    }
    @Test
    void queijoExtraDecoratorAumentaPreco() {
        ItemCardapio base = new HamburguerDirector().criarTradicional();
        ItemCardapio com  = new QueijoExtraDecorator(base);
        assertEquals(PRECO_TRADICIONAL + PRECO_QUEIJO_EXTRA, com.getPreco());
    }
    @Test
    void decoratoresAcumulamPrecos() {
        ItemCardapio base = new HamburguerDirector().criarTradicional();
        ItemCardapio com  = new BaconDecorator(new QueijoExtraDecorator(base));
        assertEquals(PRECO_TRADICIONAL + PRECO_BACON + PRECO_QUEIJO_EXTRA, com.getPreco());
    }
    @Test
    void comboSomaPrecosDeItensMaisUm() {
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Combo combo   = new Combo("Teste");
        combo.adicionar(new Produto("A", PRECO_ITEM_A, i));
        combo.adicionar(new Produto("B", PRECO_ITEM_B, i));
        assertEquals(PRECO_ITEM_A + PRECO_ITEM_B, combo.getPreco());
    }
    @Test
    void comboVazioTemPrecoZero() {
        Combo combo = new Combo("Vazio");
        assertEquals(PRECO_COMBO_VAZIO, combo.getPreco());
    }
    @Test
    void comboRetornaListaDeItens() {
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Combo combo   = new Combo("Lista");
        combo.adicionar(new Produto("A", PRECO_ITEM_A, i));
        assertEquals(1, combo.getItens().size());
    }
    @Test
    void menuComItemTemIteradorComElementos() {
        Menu menu = new Menu("Teste");
        menu.adicionar(new HamburguerDirector().criarTradicional());
        assertTrue(menu.iterator().hasNext());
    }
    @Test
    void menuVazioNaoTemElementosNoIterador() {
        Menu menu = new Menu("Vazio");
        assertFalse(menu.iterator().hasNext());
    }
    @Test
    void menuRetornaCategoriaCorreta() {
        Menu menu = new Menu("Lanches");
        assertEquals("Lanches", menu.getCategoria());
    }
    @Test
    void builderDefineNomeNoHamburguer() {
        Hamburguer h = new HamburguerBuilder().nome("X-Especial").build();
        assertEquals("X-Especial", h.getNome());
    }
    @Test
    void builderDefineCarneNoHamburguer() {
        Hamburguer h = new HamburguerBuilder().carne("Wagyu").build();
        assertEquals("Wagyu", h.getCarne());
    }
    @Test
    void builderDefineQueijoNoHamburguer() {
        Hamburguer h = new HamburguerBuilder().queijo("Gruyère").build();
        assertEquals("Gruyère", h.getQueijo());
    }
    @Test
    void builderDefineMolhoNoHamburguer() {
        Hamburguer h = new HamburguerBuilder().molho("Especial").build();
        assertEquals("Especial", h.getMolho());
    }
    @Test
    void builderDefinePaoNoHamburguer() {
        Hamburguer h = new HamburguerBuilder().pao("Brioche").build();
        assertEquals("Brioche", h.getPao());
    }
    @Test
    void builderComBaconAtivaFlag() {
        Hamburguer h = new HamburguerBuilder().comBacon().build();
        assertTrue(h.isBacon());
    }
    @Test
    void builderComSaladaAtivaFlag() {
        Hamburguer h = new HamburguerBuilder().comSalada().build();
        assertTrue(h.isSalada());
    }
    @Test
    void builderSemBaconMantemFlagFalsa() {
        Hamburguer h = new HamburguerBuilder().nome("Sem Bacon").build();
        assertFalse(h.isBacon());
    }
    @Test
    void clonePreservaNomeDoOriginal() {
        Hamburguer original = new HamburguerDirector().criarTradicional();
        Hamburguer clone    = original.clone();
        assertEquals(original.getNome(), clone.getNome());
    }
    @Test
    void clonePreservaPrecoDoOriginal() {
        Hamburguer original = new HamburguerDirector().criarPremium();
        Hamburguer clone    = original.clone();
        assertEquals(original.getPreco(), clone.getPreco());
    }
    @Test
    void comboRemoveItemCorretamente() {
        Ingrediente i  = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Produto item   = new Produto("Removível", PRECO_ITEM_A, i);
        Combo combo    = new Combo("Teste");
        combo.adicionar(item);
        combo.remover(item);
        assertEquals(PRECO_COMBO_VAZIO, combo.getPreco());
    }
    @Test
    void comboNomeEstaCorreto() {
        Combo combo = new Combo("Combo Especial");
        assertEquals("Combo Especial", combo.getNome());
    }
    @Test
    void cacheAumentaAoAdicionarNovoIngrediente() {
        int antes = IngredienteCache.totalInstancias();
        IngredienteCache.getIngrediente("NovoIngredienteUnico123", false);
        assertTrue(IngredienteCache.totalInstancias() > antes);
    }
    @Test
    void produtoGuardaNome() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_QUEIJO);
        Produto p = new Produto("X-Burger", PRECO_XBURGER, i);
        assertEquals("X-Burger", p.getNome());
    }
    @Test
    void produtoGuardaPreco() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_QUEIJO);
        Produto p = new Produto("X-Burger", PRECO_XBURGER, i);
        assertEquals(PRECO_XBURGER, p.getPreco());
    }
    @Test
    void produtoTradicionalNaoEVegano() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_QUEIJO);
        Produto p = new Produto("X-Burger", PRECO_XBURGER, i);
        assertFalse(p.isVegano());
    }
    @Test
    void produtoVeganoEhVegano() {
        Ingrediente i = new IngredienteVegano(INGREDIENTE_QUEIJO);
        Produto p = new Produto("X-Vegano", PRECO_XBURGER, i);
        assertTrue(p.isVegano());
    }
    @Test
    void produtoToStringNaoEhNulo() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_PAO);
        Produto p = new Produto("X-Burger", PRECO_XBURGER, i);
        assertNotNull(p.toString());
    }
    @Test
    void ingredienteTradicionalDescricaoContemNome() {
        Ingrediente i = new IngredienteTradicional(INGREDIENTE_QUEIJO);
        assertTrue(i.getDescricao().contains(INGREDIENTE_QUEIJO));
    }
    @Test
    void ingredienteVeganoDescricaoContemNome() {
        Ingrediente i = new IngredienteVegano(INGREDIENTE_QUEIJO);
        assertTrue(i.getDescricao().contains(INGREDIENTE_QUEIJO));
    }
    @Test
    void ingredienteVeganoRetornaNomeCorreto() {
        Ingrediente i = new IngredienteVegano(INGREDIENTE_BATATA);
        assertEquals(INGREDIENTE_BATATA, i.getNome());
    }
    @Test
    void hamburguerPremiumTemNomeCorreto() {
        Hamburguer h = new HamburguerDirector().criarPremium();
        assertEquals("X-Premium", h.getNome());
    }
    @Test
    void hamburguerPremiumTemBacon() {
        Hamburguer h = new HamburguerDirector().criarPremium();
        assertTrue(h.isBacon());
    }
    @Test
    void hamburguerPremiumPrecoMaiorQueTradicional() {
        Hamburguer trad    = new HamburguerDirector().criarTradicional();
        Hamburguer premium = new HamburguerDirector().criarPremium();
        assertTrue(premium.getPreco() > trad.getPreco());
    }
    @Test
    void hamburguerExtraIngredienteAdicionadoAoExtras() {
        Hamburguer h = new HamburguerBuilder()
                .nome("Teste")
                .extraIngrediente(INGREDIENTE_QUEIJO, false)
                .build();
        assertFalse(h.getExtras().isEmpty());
    }
    @Test
    void hamburguerSemExtraListaVazia() {
        Hamburguer h = new HamburguerBuilder().nome("Simples").build();
        assertTrue(h.getExtras().isEmpty());
    }
    @Test
    void familiaTradicionalCriaBatataComPrecoPositivo() {
        CardapioFactory f = new CardapioTradicionalFactory();
        assertTrue(f.criarBatata().getPreco() > 0);
    }
    @Test
    void familiaVeganaCriaBatataComPrecoPositivo() {
        CardapioFactory f = new CardapioVeganoFactory();
        assertTrue(f.criarBatata().getPreco() > 0);
    }
    @Test
    void familiaTradicionalCriaBebidaComPrecoPositivo() {
        CardapioFactory f = new CardapioTradicionalFactory();
        assertTrue(f.criarBebida().getPreco() > 0);
    }
    @Test
    void queijoExtraDecoratorContemNomeOriginal() {
        ItemCardapio base = new HamburguerDirector().criarTradicional();
        ItemCardapio com  = new QueijoExtraDecorator(base);
        assertTrue(com.getNome().contains(base.getNome()));
    }
    @Test
    void baconDecoratorContemNomeOriginal() {
        ItemCardapio base = new HamburguerDirector().criarVegano();
        ItemCardapio com  = new BaconDecorator(base);
        assertTrue(com.getNome().contains(base.getNome()));
    }
    @Test
    void comboComDoisItensTemTamanhoCorreto() {
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Combo combo   = new Combo("Dois");
        combo.adicionar(new Produto("A", PRECO_ITEM_A, i));
        combo.adicionar(new Produto("B", PRECO_ITEM_B, i));
        assertEquals(2, combo.getItens().size());
    }
    @Test
    void menuComDoisItensSegundoItemAcessivelViaIterador() {
        Menu menu = new Menu("Teste");
        Hamburguer h1 = new HamburguerDirector().criarTradicional();
        Hamburguer h2 = new HamburguerDirector().criarVegano();
        menu.adicionar(h1);
        menu.adicionar(h2);
        java.util.Iterator<ItemCardapio> it = menu.iterator();
        it.next();
        assertTrue(it.hasNext());
    }
}
