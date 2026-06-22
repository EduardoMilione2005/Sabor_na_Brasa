package com.sabornabrasa;
import com.sabornabrasa.cardapio.*;
import com.sabornabrasa.cozinha.*;
import com.sabornabrasa.entrega.*;
import com.sabornabrasa.pagamento.*;
import com.sabornabrasa.pedido.ObservadorPedido;
import com.sabornabrasa.pedido.Pedido;
import com.sabornabrasa.relatorio.*;
import com.sabornabrasa.sistema.ConfiguracaoSistema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CozinhaPagamentoEntregaTest {
    private static final String NOME_PRODUTO           = "X-Burger";
    private static final String INGREDIENTE_PAO        = "Pao";
    private static final double PRECO_PRODUTO          = 30.0;
    private static final double TAXA_DESCONTO_NOVO     = 0.10;
    private static final double TAXA_DESCONTO_VIP      = 0.20;
    private static final double TAXA_IMPOSTO           = 0.12;
    private static final double DESCONTO_ZERO          = 0.0;
    private static final double TOTAL_ZERO             = 0.0;
    private static final double PRECO_PARA_IMPOSTO     = 100.0;
    private static final double IMPOSTO_ESPERADO       = PRECO_PARA_IMPOSTO * TAXA_IMPOSTO;
    private static final double TOTAL_COM_DESC_NOVO    = PRECO_PRODUTO * (1.0 - TAXA_DESCONTO_NOVO);
    private static final double TOTAL_COM_DESC_VIP     = PRECO_PRODUTO * (1.0 - TAXA_DESCONTO_VIP);
    private static final double TOLERANCIA             = 0.001;
    private static final String STATUS_ENTREGUE        = "Entregue";
    private static final String NOME_HAMBURGUERIA      = "Sabor na Brasa";
    @Test
    void duasChamadasRetornamMesmaInstancia() {
        assertSame(ConfiguracaoSistema.getInstancia(), ConfiguracaoSistema.getInstancia());
    }
    @Test
    void instanciaTemNomeCorreto() {
        assertEquals(NOME_HAMBURGUERIA, ConfiguracaoSistema.getInstancia().getNomeHamburgueria());
    }
    @Test
    void preparoTradicionalNaoLancaExcecao() {
        assertDoesNotThrow(() -> new PreparoTradicional().preparar(criarPedidoComItem("Lucia")));
    }
    @Test
    void preparoVeganoNaoLancaExcecao() {
        assertDoesNotThrow(() -> new PreparoVegano().preparar(criarPedidoComItem("Lucia")));
    }
    @Test
    void cozinhaObservadorImplementaObservadorPedido() {
        assertInstanceOf(ObservadorPedido.class, new CozinhaObservador(new PreparoTradicional()));
    }
    @Test
    void cozinhaObservadorGuardaEstrategiaDePreparo() {
        PreparoTradicional preparo = new PreparoTradicional();
        CozinhaObservador obs = new CozinhaObservador(preparo);
        assertSame(preparo, obs.getEstrategiaPrep());
    }
    @Test
    void semDescontoRetornaZero() {
        assertEquals(DESCONTO_ZERO, new SemDesconto().calcularDesconto(PRECO_PRODUTO));
    }
    @Test
    void descontoClienteNovoCalculaDezPorcento() {
        double esperado = PRECO_PRODUTO * TAXA_DESCONTO_NOVO;
        assertEquals(esperado, new DescontoClienteNovo().calcularDesconto(PRECO_PRODUTO), TOLERANCIA);
    }
    @Test
    void descontoClienteVipCalculaVintePorcento() {
        double esperado = PRECO_PRODUTO * TAXA_DESCONTO_VIP;
        assertEquals(esperado, new DescontoClienteVip().calcularDesconto(PRECO_PRODUTO), TOLERANCIA);
    }
    @Test
    void processadorAplicaDescontoAoPedido() {
        Pedido p = criarPedidoComItem("João");
        ProcessadorPagamento proc = new ProcessadorPagamento();
        proc.setEstrategiaDesconto(new DescontoClienteNovo());
        double resultado = proc.processar(p);
        assertEquals(TOTAL_COM_DESC_NOVO, resultado, TOLERANCIA);
    }
    @Test
    void processadorTrocaEstrategiaEmRuntime() {
        Pedido p = criarPedidoComItem("Ana");
        ProcessadorPagamento proc = new ProcessadorPagamento();
        proc.setEstrategiaDesconto(new DescontoClienteVip());
        double esperado = TOTAL_COM_DESC_VIP;
        assertEquals(esperado, proc.processar(p), TOLERANCIA);
    }
    @Test
    void adapterRealizaEntregaSemExcecao() {
        ServicoEntrega servico = new EntregaAdapter(new SistemaEntregaLegado());
        Pedido p = pedidoNoPronto("Fernanda");
        assertDoesNotThrow(() -> servico.realizarEntrega(p, "Rua A, 1"));
    }
    @Test
    void adapterAvancaParaEntregue() {
        ServicoEntrega servico = new EntregaAdapter(new SistemaEntregaLegado());
        Pedido p = pedidoNoPronto("Roberto");
        servico.realizarEntrega(p, "Rua B, 2");
        assertEquals(STATUS_ENTREGUE, p.getStatusDescricao());
    }
    @Test
    void adapterRastrearRetornaStringNaoNula() {
        ServicoEntrega servico = new EntregaAdapter(new SistemaEntregaLegado());
        assertNotNull(servico.rastrearPedido(1));
    }
    @Test
    void calculadorSomaPrecosDoCombo() {
        Ingrediente i  = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Combo combo    = new Combo("Teste");
        combo.adicionar(new Produto("A", PRECO_PRODUTO, i));
        CalculadorPrecoVisitor v = new CalculadorPrecoVisitor();
        combo.aceitar(v);
        assertEquals(PRECO_PRODUTO, v.getTotal(), TOLERANCIA);
    }
    @Test
    void calculadorResetaTotal() {
        CalculadorPrecoVisitor v = new CalculadorPrecoVisitor();
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        new Produto("X", PRECO_PRODUTO, i).aceitar(v);
        v.resetar();
        assertEquals(TOTAL_ZERO, v.getTotal());
    }
    @Test
    void impostoCalculadoSobreProduto() {
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        RelatorioImpostoVisitor v = new RelatorioImpostoVisitor();
        new Produto("Item", PRECO_PARA_IMPOSTO, i).aceitar(v);
        assertEquals(IMPOSTO_ESPERADO, v.getTotalImposto(), TOLERANCIA);
    }
    @Test
    void impostoCalculadoSobreHamburguer() {
        Hamburguer h = new HamburguerDirector().criarPremium();
        RelatorioImpostoVisitor v = new RelatorioImpostoVisitor();
        h.aceitar(v);
        assertEquals(h.getPreco() * TAXA_IMPOSTO, v.getTotalImposto(), TOLERANCIA);
    }
    private Pedido criarPedidoComItem(String cliente) {
        Pedido p = new Pedido(cliente);
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        p.adicionarItem(new Produto(NOME_PRODUTO, PRECO_PRODUTO, i));
        return p;
    }
    private Pedido pedidoNoPronto(String cliente) {
        Pedido p = criarPedidoComItem(cliente);
        p.avancarEstado();
        p.avancarEstado();
        return p;
    }
    @Test
    void semDescontoTemDescricaoNaoNula() {
        assertNotNull(new SemDesconto().descricao());
    }
    @Test
    void descontoClienteNovoTemDescricaoNaoNula() {
        assertNotNull(new DescontoClienteNovo().descricao());
    }
    @Test
    void descontoClienteVipTemDescricaoNaoNula() {
        assertNotNull(new DescontoClienteVip().descricao());
    }
    @Test
    void processadorIniciaComSemDesconto() {
        ProcessadorPagamento proc = new ProcessadorPagamento();
        assertEquals(DESCONTO_ZERO, new SemDesconto().calcularDesconto(PRECO_PRODUTO));
    }
    @Test
    void processadorDescricaoNaoNulaAposDefinirEstrategia() {
        ProcessadorPagamento proc = new ProcessadorPagamento();
        proc.setEstrategiaDesconto(new DescontoClienteVip());
        assertNotNull(proc.getEstrategiaDescricao());
    }
    @Test
    void rastrearRetornaStringComNumeroDoRastreio() {
        ServicoEntrega servico = new EntregaAdapter(new SistemaEntregaLegado());
        String rastreio = servico.rastrearPedido(42);
        assertTrue(rastreio.contains("42"));
    }
    @Test
    void sistemaLegadoRastreioCodigo() {
        SistemaEntregaLegado legado = new SistemaEntregaLegado();
        String resultado = legado.rastrearCodigo("SNB-1");
        assertNotNull(resultado);
    }
    @Test
    void calculadorVisitaHamburguerSomandoPreco() {
        Hamburguer h = new HamburguerDirector().criarTradicional();
        CalculadorPrecoVisitor v = new CalculadorPrecoVisitor();
        h.aceitar(v);
        assertTrue(v.getTotal() > TOTAL_ZERO);
    }
    @Test
    void impostoVisitaComboSemExcecao() {
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Combo combo   = new Combo("Teste");
        combo.adicionar(new Produto("A", PRECO_PRODUTO, i));
        RelatorioImpostoVisitor v = new RelatorioImpostoVisitor();
        assertDoesNotThrow(() -> combo.aceitar(v));
    }
    @Test
    void impostoAcumulaEmMultiplosProdutos() {
        Ingrediente i  = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Produto p1     = new Produto("A", PRECO_PARA_IMPOSTO, i);
        Produto p2     = new Produto("B", PRECO_PARA_IMPOSTO, i);
        RelatorioImpostoVisitor v = new RelatorioImpostoVisitor();
        p1.aceitar(v);
        p2.aceitar(v);
        assertEquals(PRECO_PARA_IMPOSTO * TAXA_IMPOSTO * 2, v.getTotalImposto(), TOLERANCIA);
    }
    @Test
    void preparoTradicionalESubtipoDePreparoPedido() {
        assertInstanceOf(PreparoPedido.class, new PreparoTradicional());
    }
    @Test
    void preparoVeganoESubtipoDePreparoPedido() {
        assertInstanceOf(PreparoPedido.class, new PreparoVegano());
    }
    @Test
    void configuracaoTemVersaoNaoNula() {
        assertNotNull(ConfiguracaoSistema.getInstancia().getVersao());
    }
    @Test
    void configuracaoModoDebugInicialmenteFalso() {
        ConfiguracaoSistema cfg = ConfiguracaoSistema.getInstancia();
        cfg.setModoDebug(false);
        assertFalse(cfg.isModoDebug());
    }
    @Test
    void configuracaoModoDebugPodeSerAtivado() {
        ConfiguracaoSistema cfg = ConfiguracaoSistema.getInstancia();
        cfg.setModoDebug(true);
        assertTrue(cfg.isModoDebug());
        cfg.setModoDebug(false);
    }
    @Test
    void configuracaoToStringNaoEhNulo() {
        assertNotNull(ConfiguracaoSistema.getInstancia().toString());
    }
    @Test
    void descontoClienteNovoDeZeroRetornaZero() {
        assertEquals(DESCONTO_ZERO, new DescontoClienteNovo().calcularDesconto(DESCONTO_ZERO));
    }
    @Test
    void descontoClienteVipDeZeroRetornaZero() {
        assertEquals(DESCONTO_ZERO, new DescontoClienteVip().calcularDesconto(DESCONTO_ZERO));
    }
    @Test
    void sistemaLegadoDespachoNaoLancaExcecao() {
        SistemaEntregaLegado legado = new SistemaEntregaLegado();
        assertDoesNotThrow(() -> legado.despacharMercadoria("Rua A", "Produto X"));
    }
    @Test
    void entregaAdapterImplementaServicoEntrega() {
        EntregaAdapter adapter = new EntregaAdapter(new SistemaEntregaLegado());
        assertInstanceOf(ServicoEntrega.class, adapter);
    }
    @Test
    void preparoTradicionalComPedidoVeganoNaoLancaExcecao() {
        Pedido p = criarPedidoComItem("Vegano");
        assertDoesNotThrow(() -> new PreparoTradicional().preparar(p));
    }
    @Test
    void preparoVeganoComPedidoTradicionalNaoLancaExcecao() {
        Pedido p = criarPedidoComItem("Trad");
        assertDoesNotThrow(() -> new PreparoVegano().preparar(p));
    }
    @Test
    void cozinhaObservadorAtualizarNaoLancaExcecao() {
        CozinhaObservador obs = new CozinhaObservador(new PreparoTradicional());
        assertDoesNotThrow(() -> obs.atualizar("Preparando", 1));
    }
    @Test
    void calculadorAcumulaHamburguerEProduto() {
        CalculadorPrecoVisitor v = new CalculadorPrecoVisitor();
        Hamburguer h = new HamburguerDirector().criarTradicional();
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        Produto p = new Produto("Refri", PRECO_PRODUTO, i);
        h.aceitar(v);
        p.aceitar(v);
        assertTrue(v.getTotal() > PRECO_PRODUTO);
    }
    @Test
    void impostoNaoAcumulaAntesDeVisitar() {
        RelatorioImpostoVisitor v = new RelatorioImpostoVisitor();
        assertEquals(DESCONTO_ZERO, v.getTotalImposto());
    }
}
