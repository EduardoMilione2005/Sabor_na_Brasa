package com.sabornabrasa;
import com.sabornabrasa.atendimento.Solicitacao;
import com.sabornabrasa.core.HamburgueriaFacade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FacadeIntegracaoTest {
    private static final String ENDERECO          = "Av. Paulista, 1000";
    private static final String CLIENTE           = "Fernanda";
    private static final String DESCRICAO         = "Quero reembolso";
    private static final String ITEM_PEDIDO       = "Combo";
    private static final int    NUMERO_PEDIDO     = 1;
    private static final int    HISTORICO_MINIMO  = 1;
    @Test
    void pedidoTradicionalCompletaNaoLancaExcecao() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        assertDoesNotThrow(() -> facade.realizarPedidoTradicional(CLIENTE, ENDERECO));
    }
    @Test
    void exibirCardapioTradicionalNaoLancaExcecao() {
        assertDoesNotThrow(() -> new HamburgueriaFacade().exibirCardapio(false));
    }
    @Test
    void exibirCardapioVeganoNaoLancaExcecao() {
        assertDoesNotThrow(() -> new HamburgueriaFacade().exibirCardapio(true));
    }
    @Test
    void registrarPedidoSimplesNaoLancaExcecao() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        Solicitacao s = new Solicitacao(Solicitacao.Tipo.PEDIDO_SIMPLES, DESCRICAO, CLIENTE, ITEM_PEDIDO);
        assertDoesNotThrow(() -> facade.registrarSolicitacao(s));
    }
    @Test
    void registrarReembolsoNaoLancaExcecao() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        Solicitacao s = new Solicitacao(Solicitacao.Tipo.REEMBOLSO, DESCRICAO, CLIENTE, ITEM_PEDIDO);
        assertDoesNotThrow(() -> facade.registrarSolicitacao(s));
    }
    @Test
    void rastrearPedidoRetornaStringNaoNula() {
        assertNotNull(new HamburgueriaFacade().rastrearPedido(NUMERO_PEDIDO));
    }
    @Test
    void historicoTemAoMenosUmEstadoAposPedido() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        facade.realizarPedidoTradicional(CLIENTE, ENDERECO);
        assertTrue(facade.getHistorico().tamanho() >= HISTORICO_MINIMO);
    }
    @Test
    void registrarReclamacaoNaoLancaExcecao() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        Solicitacao s = new Solicitacao(Solicitacao.Tipo.RECLAMACAO, DESCRICAO, CLIENTE, ITEM_PEDIDO);
        assertDoesNotThrow(() -> facade.registrarSolicitacao(s));
    }
    @Test
    void registrarProblemaGraveNaoLancaExcecao() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        Solicitacao s = new Solicitacao(Solicitacao.Tipo.PROBLEMA_GRAVE, DESCRICAO, CLIENTE, ITEM_PEDIDO);
        assertDoesNotThrow(() -> facade.registrarSolicitacao(s));
    }
    @Test
    void rastrearNumeroDiferenteRetornaString() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        assertNotNull(facade.rastrearPedido(99));
    }
    @Test
    void historicoEhNuloAntesDeQualquerPedido() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        assertNotNull(facade.getHistorico());
    }
    @Test
    void doisPedidosAumentamHistorico() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        facade.realizarPedidoTradicional(CLIENTE, ENDERECO);
        facade.realizarPedidoTradicional("Ana", ENDERECO);
        assertTrue(facade.getHistorico().tamanho() >= 2);
    }
    @Test
    void pedidoComClienteDiferenteNaoLancaExcecao() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        assertDoesNotThrow(() -> facade.realizarPedidoTradicional("Carlos", ENDERECO));
    }
    @Test
    void rastrearPedidoComZeroNaoLancaExcecao() {
        assertDoesNotThrow(() -> new HamburgueriaFacade().rastrearPedido(0));
    }
    @Test
    void historicoInicialmenteVazio() {
        HamburgueriaFacade facade = new HamburgueriaFacade();
        assertEquals(0, facade.getHistorico().tamanho());
    }
    @Test
    void duasChamadasFacadeIndependentes() {
        HamburgueriaFacade f1 = new HamburgueriaFacade();
        HamburgueriaFacade f2 = new HamburgueriaFacade();
        assertNotSame(f1, f2);
    }
}
