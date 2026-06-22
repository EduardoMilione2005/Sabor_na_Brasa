package com.sabornabrasa;
import com.sabornabrasa.cardapio.*;
import com.sabornabrasa.pedido.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {
    private static final String NOME_CLIENTE       = "Cliente Teste";
    private static final String NOME_PRODUTO       = "X-Burger";
    private static final String INGREDIENTE_CARNE  = "Carne";
    private static final double PRECO_PRODUTO      = 30.0;
    private static final double DESCONTO_FIXO      = 5.0;
    private static final double TOTAL_COM_DESCONTO = PRECO_PRODUTO - DESCONTO_FIXO;
    private static final String STATUS_RECEBIDO    = "Recebido";
    private static final String STATUS_PREPARANDO  = "Preparando";
    private static final String STATUS_PRONTO      = "Pronto";
    private static final String STATUS_ENTREGUE    = "Entregue";
    private static final String STATUS_CANCELADO   = "Cancelado";
    private Pedido pedido;
    @BeforeEach
    void criarPedidoComItem() {
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_CARNE, false);
        pedido = new Pedido(NOME_CLIENTE);
        pedido.adicionarItem(new Produto(NOME_PRODUTO, PRECO_PRODUTO, i));
    }
    @Test
    void estadoInicialERecebido() {
        assertEquals(STATUS_RECEBIDO, pedido.getStatusDescricao());
    }
    @Test
    void umAvancoLevaParaPreparando() {
        pedido.avancarEstado();
        assertEquals(STATUS_PREPARANDO, pedido.getStatusDescricao());
    }
    @Test
    void doisAvancosLevaParaPronto() {
        pedido.avancarEstado();
        pedido.avancarEstado();
        assertEquals(STATUS_PRONTO, pedido.getStatusDescricao());
    }
    @Test
    void tresAvancosLevaParaEntregue() {
        pedido.avancarEstado();
        pedido.avancarEstado();
        pedido.avancarEstado();
        assertEquals(STATUS_ENTREGUE, pedido.getStatusDescricao());
    }
    @Test
    void cancelarEmRecebidoMudaParaCancelado() {
        pedido.cancelar();
        assertEquals(STATUS_CANCELADO, pedido.getStatusDescricao());
    }
    @Test
    void cancelarEmPreparandoMantemEstado() {
        pedido.avancarEstado();
        pedido.cancelar();
        assertEquals(STATUS_PREPARANDO, pedido.getStatusDescricao());
    }
    @Test
    void observadorRecebeStatusAposAvancar() {
        String[] capturado = {null};
        pedido.assinar((status, num) -> capturado[0] = status);
        pedido.avancarEstado();
        assertEquals(STATUS_PREPARANDO, capturado[0]);
    }
    @Test
    void observadorRecebeNumeroDoPedido() {
        int[] capturado = {0};
        pedido.assinar((status, num) -> capturado[0] = num);
        pedido.avancarEstado();
        assertEquals(pedido.getNumero(), capturado[0]);
    }
    @Test
    void observadorRemovidoNaoRecebeNotificacao() {
        String[] capturado = {null};
        ObservadorPedido obs = (status, num) -> capturado[0] = status;
        pedido.assinar(obs);
        pedido.cancelarAssinatura(obs);
        pedido.avancarEstado();
        assertNull(capturado[0]);
    }
    @Test
    void snapshotGuardaEstadoDoMomento() {
        PedidoMemento snap = pedido.salvarEstado();
        assertEquals(STATUS_RECEBIDO, snap.getEstado());
    }
    @Test
    void snapshotGuardaTotalDoMomento() {
        PedidoMemento snap = pedido.salvarEstado();
        assertEquals(PRECO_PRODUTO, snap.getTotalNaMomento());
    }
    @Test
    void snapshotGuardaNomeDoCliente() {
        PedidoMemento snap = pedido.salvarEstado();
        assertEquals(NOME_CLIENTE, snap.getNomeCliente());
    }
    @Test
    void historicoVazioTemTamanhoZero() {
        HistoricoPedido h = new HistoricoPedido();
        assertEquals(0, h.tamanho());
    }
    @Test
    void historicoAumentaAoSalvar() {
        HistoricoPedido h = new HistoricoPedido();
        h.salvar(pedido.salvarEstado());
        assertEquals(1, h.tamanho());
    }
    @Test
    void historicoDesfazerRetornaUltimoEstado() {
        HistoricoPedido h = new HistoricoPedido();
        h.salvar(pedido.salvarEstado());
        pedido.avancarEstado();
        h.salvar(pedido.salvarEstado());
        PedidoMemento ultimo = h.desfazer();
        assertEquals(STATUS_PREPARANDO, ultimo.getEstado());
    }
    @Test
    void executarComandoAvancaEstado() {
        FilaComandos fila = new FilaComandos();
        fila.executar(new AvancarPedidoCommand(pedido));
        assertEquals(STATUS_PREPARANDO, pedido.getStatusDescricao());
    }
    @Test
    void desfazerComandoRevertEstado() {
        FilaComandos fila = new FilaComandos();
        fila.executar(new AvancarPedidoCommand(pedido));
        fila.desfazerUltimo();
        assertEquals(STATUS_RECEBIDO, pedido.getStatusDescricao());
    }
    @Test
    void proxyBloqueaPedidoSemItens() {
        Pedido vazio = new Pedido("Sem Itens");
        PedidoProxy proxy = new PedidoProxy(new FilaComandos(), new HistoricoPedido());
        proxy.processarAvancar(vazio);
        assertEquals(STATUS_RECEBIDO, vazio.getStatusDescricao());
    }
    @Test
    void proxyAvancaPedidoComItens() {
        PedidoProxy proxy = new PedidoProxy(new FilaComandos(), new HistoricoPedido());
        proxy.processarAvancar(pedido);
        assertEquals(STATUS_PREPARANDO, pedido.getStatusDescricao());
    }
    @Test
    void proxyRegistraNoHistoricoAoAvancar() {
        HistoricoPedido historico = new HistoricoPedido();
        PedidoProxy proxy = new PedidoProxy(new FilaComandos(), historico);
        proxy.processarAvancar(pedido);
        assertEquals(1, historico.tamanho());
    }
    @Test
    void interpretarTradicionalRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("TRADICIONAL");
        assertTrue(resultado.contains("Hamburguer Tradicional"));
    }
    @Test
    void interpretarTokenDesconhecidoMarcaComInterrogacao() {
        String resultado = new InterpretadorPedido().interpretar("PIZZA");
        assertTrue(resultado.contains("??PIZZA??"));
    }
    @Test
    void totalSemDescontoEhPrecoDoItem() {
        assertEquals(PRECO_PRODUTO, pedido.getTotal());
    }
    @Test
    void totalComDescontoEhDescontado() {
        pedido.setDesconto(DESCONTO_FIXO);
        assertEquals(TOTAL_COM_DESCONTO, pedido.getTotal());
    }
    @Test
    void cancelarEmProntoMantemEstado() {
        pedido.avancarEstado();
        pedido.avancarEstado();
        pedido.cancelar();
        assertEquals(STATUS_PRONTO, pedido.getStatusDescricao());
    }
    @Test
    void cancelarEmEntregueMantemEstado() {
        pedido.avancarEstado();
        pedido.avancarEstado();
        pedido.avancarEstado();
        pedido.cancelar();
        assertEquals(STATUS_ENTREGUE, pedido.getStatusDescricao());
    }
    @Test
    void avancaEmCanceladoMantemEstado() {
        pedido.cancelar();
        pedido.avancarEstado();
        assertEquals(STATUS_CANCELADO, pedido.getStatusDescricao());
    }
    @Test
    void avancaEmEntregueMantemEstado() {
        pedido.avancarEstado();
        pedido.avancarEstado();
        pedido.avancarEstado();
        pedido.avancarEstado();
        assertEquals(STATUS_ENTREGUE, pedido.getStatusDescricao());
    }
    @Test
    void pedidoGuardaNomeDoCliente() {
        assertEquals(NOME_CLIENTE, pedido.getNomeCliente());
    }
    @Test
    void pedidoTotalEhSomaDosItens() {
        assertEquals(PRECO_PRODUTO, pedido.getTotal());
    }
    @Test
    void pedidoNumeroEPositivo() {
        assertTrue(pedido.getNumero() > 0);
    }
    @Test
    void doisPedidosTemNumerosDiferentes() {
        Pedido outro = new Pedido("Outro");
        assertNotEquals(pedido.getNumero(), outro.getNumero());
    }
    @Test
    void snapshotTemTimestampPositivo() {
        PedidoMemento snap = pedido.salvarEstado();
        assertTrue(snap.getTimestamp() > 0);
    }
    @Test
    void comandoDescricaoContemNumeroDoPedido() {
        AvancarPedidoCommand cmd = new AvancarPedidoCommand(pedido);
        assertTrue(cmd.descricao().contains(String.valueOf(pedido.getNumero())));
    }
    @Test
    void interpretarBaconRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("BACON");
        assertTrue(resultado.contains("Bacon"));
    }
    @Test
    void interpretarRefriRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("REFRI");
        assertTrue(resultado.contains("Refrigerante"));
    }
    @Test
    void interpretarBatataRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("BATATA");
        assertTrue(resultado.contains("Batata"));
    }
    @Test
    void interpretarVeganoRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("VEGANO");
        assertTrue(resultado.contains("Vegano"));
    }
    @Test
    void pedidoComDoisItensSomaTotais() {
        Ingrediente i  = IngredienteCache.getIngrediente(INGREDIENTE_CARNE, false);
        Produto extra  = new Produto("Extra", PRECO_PRODUTO, i);
        pedido.adicionarItem(extra);
        assertEquals(PRECO_PRODUTO * 2, pedido.getTotal());
    }
    @Test
    void pedidoListaItensNaoEhNula() {
        assertNotNull(pedido.getItens());
    }
    @Test
    void pedidoTemUmItemAposCriar() {
        assertEquals(1, pedido.getItens().size());
    }
    @Test
    void pedidoNomeClienteNaoEhNulo() {
        assertNotNull(pedido.getNomeCliente());
    }
    @Test
    void doisObservadoresRecebeNotificacao() {
        String[] cap1 = {null};
        String[] cap2 = {null};
        pedido.assinar((s, n) -> cap1[0] = s);
        pedido.assinar((s, n) -> cap2[0] = s);
        pedido.avancarEstado();
        assertNotNull(cap1[0]);
    }
    @Test
    void segundoObservadorTambemRecebe() {
        String[] cap1 = {null};
        String[] cap2 = {null};
        pedido.assinar((s, n) -> cap1[0] = s);
        pedido.assinar((s, n) -> cap2[0] = s);
        pedido.avancarEstado();
        assertNotNull(cap2[0]);
    }
    @Test
    void historicoListarTodosRetornaLista() {
        HistoricoPedido h = new HistoricoPedido();
        h.salvar(pedido.salvarEstado());
        assertNotNull(h.listarTodos());
    }
    @Test
    void historicoListarTodosTemTamanhoCorreto() {
        HistoricoPedido h = new HistoricoPedido();
        h.salvar(pedido.salvarEstado());
        h.salvar(pedido.salvarEstado());
        assertEquals(2, h.listarTodos().size());
    }
    @Test
    void historicoDesfazerVazioRetornaNull() {
        HistoricoPedido h = new HistoricoPedido();
        assertNull(h.desfazer());
    }
    @Test
    void historicoDiminuiAposDesfazer() {
        HistoricoPedido h = new HistoricoPedido();
        h.salvar(pedido.salvarEstado());
        h.desfazer();
        assertEquals(0, h.tamanho());
    }
    @Test
    void desfazerSemComandoNaoLancaExcecao() {
        FilaComandos fila = new FilaComandos();
        assertDoesNotThrow(fila::desfazerUltimo);
    }
    @Test
    void executarDoisComandosAvancaDoisEstados() {
        FilaComandos fila = new FilaComandos();
        fila.executar(new AvancarPedidoCommand(pedido));
        fila.executar(new AvancarPedidoCommand(pedido));
        assertEquals(STATUS_PRONTO, pedido.getStatusDescricao());
    }
    @Test
    void desfazerDoisComandosVoltaEstadoInicial() {
        FilaComandos fila = new FilaComandos();
        fila.executar(new AvancarPedidoCommand(pedido));
        fila.executar(new AvancarPedidoCommand(pedido));
        fila.desfazerUltimo();
        fila.desfazerUltimo();
        assertEquals(STATUS_RECEBIDO, pedido.getStatusDescricao());
    }
    @Test
    void proxyCancelamentoPedidoComItens() {
        PedidoProxy proxy = new PedidoProxy(new FilaComandos(), new HistoricoPedido());
        proxy.processarCancelamento(pedido);
        assertEquals(STATUS_CANCELADO, pedido.getStatusDescricao());
    }
    @Test
    void proxyCancelamentoRegistraHistorico() {
        HistoricoPedido historico = new HistoricoPedido();
        PedidoProxy proxy = new PedidoProxy(new FilaComandos(), historico);
        proxy.processarCancelamento(pedido);
        assertEquals(1, historico.tamanho());
    }
    @Test
    void interpretarPremiumRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("PREMIUM");
        assertTrue(resultado.contains("Premium"));
    }
    @Test
    void interpretarQueijoRetornaDescricao() {
        String resultado = new InterpretadorPedido().interpretar("QUEIJO");
        assertTrue(resultado.contains("Queijo"));
    }
    @Test
    void mementoToStringNaoEhNulo() {
        assertNotNull(pedido.salvarEstado().toString());
    }
}
