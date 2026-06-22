package com.sabornabrasa;
import com.sabornabrasa.atendimento.*;
import com.sabornabrasa.cardapio.*;
import com.sabornabrasa.pedido.ObservadorPedido;
import com.sabornabrasa.pedido.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AtendimentoTest {
    private static final String NOME_CLIENTE       = "Maria";
    private static final String NOME_PRODUTO       = "X-Burger";
    private static final String INGREDIENTE_PAO    = "Pao";
    private static final double PRECO_PRODUTO      = 28.0;
    private static final String STATUS_PREPARANDO  = "Preparando";
    private static final String STATUS_PRONTO      = "Pronto";
    private ManipuladorAtendimento cadeia;
    @BeforeEach
    void montarCadeia() {
        Atendente atendente = new Atendente();
        Gerente   gerente   = new Gerente();
        Dono      dono      = new Dono();
        atendente.setProximo(gerente).setProximo(dono);
        cadeia = atendente;
    }
    @Test
    void atendenteProcessaPedidoSimplesemExcecao() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.PEDIDO_SIMPLES);
        assertDoesNotThrow(() -> cadeia.atender(s));
    }
    @Test
    void gerenteProcessaReclamacaoSemExcecao() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.RECLAMACAO);
        assertDoesNotThrow(() -> cadeia.atender(s));
    }
    @Test
    void gerenteProcessaReembolsoSemExcecao() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.REEMBOLSO);
        assertDoesNotThrow(() -> cadeia.atender(s));
    }
    @Test
    void donoProcessaProblemaGraveSemExcecao() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.PROBLEMA_GRAVE);
        assertDoesNotThrow(() -> cadeia.atender(s));
    }
    @Test
    void mediadorAvancaPedidoAoRegistrar() {
        MediadorAtendimento mediador = new CentralAtendimento();
        Pedido pedido = criarPedidoComItem("Pedro");
        mediador.clienteFezPedido("Pedro", pedido);
        assertEquals(STATUS_PREPARANDO, pedido.getStatusDescricao());
    }
    @Test
    void mediadorAvancaPedidoAoFinalizar() {
        MediadorAtendimento mediador = new CentralAtendimento();
        Pedido pedido = criarPedidoComItem("Lucia");
        pedido.avancarEstado();
        mediador.cozinhaFinalizouPedido(pedido);
        assertEquals(STATUS_PRONTO, pedido.getStatusDescricao());
    }
    @Test
    void clienteObservadorImplementaObservadorPedido() {
        assertInstanceOf(ObservadorPedido.class, new ClienteObservador(NOME_CLIENTE));
    }
    @Test
    void clienteObservadorGuardaNome() {
        ClienteObservador obs = new ClienteObservador(NOME_CLIENTE);
        assertEquals(NOME_CLIENTE, obs.getNome());
    }
    @Test
    void clienteObservadorENotificadoAoMudarEstado() {
        Pedido pedido = criarPedidoComItem(NOME_CLIENTE);
        String[] capturado = {null};
        pedido.assinar((status, num) -> capturado[0] = status);
        pedido.avancarEstado();
        assertEquals(STATUS_PREPARANDO, capturado[0]);
    }
    private Solicitacao solicitacao(Solicitacao.Tipo tipo) {
        return new Solicitacao(tipo, "descricao", NOME_CLIENTE, NOME_PRODUTO);
    }
    private Pedido criarPedidoComItem(String cliente) {
        Pedido p = new Pedido(cliente);
        Ingrediente i = IngredienteCache.getIngrediente(INGREDIENTE_PAO, false);
        p.adicionarItem(new Produto(NOME_PRODUTO, PRECO_PRODUTO, i));
        return p;
    }
    @Test
    void solicitacaoGuardaTipo() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.PEDIDO_SIMPLES);
        assertEquals(Solicitacao.Tipo.PEDIDO_SIMPLES, s.getTipo());
    }
    @Test
    void solicitacaoGuardaNomeCliente() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.RECLAMACAO);
        assertEquals(NOME_CLIENTE, s.getNomeCliente());
    }
    @Test
    void solicitacaoGuardaItemPedido() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.REEMBOLSO);
        assertEquals(NOME_PRODUTO, s.getPedido());
    }
    @Test
    void atendenteIsoladoProcessaPedidoSimples() {
        Atendente atendente = new Atendente();
        Solicitacao s = solicitacao(Solicitacao.Tipo.PEDIDO_SIMPLES);
        assertDoesNotThrow(() -> atendente.atender(s));
    }
    @Test
    void gerenteIsoladoProcessaReclamacao() {
        Gerente gerente = new Gerente();
        Solicitacao s = solicitacao(Solicitacao.Tipo.RECLAMACAO);
        assertDoesNotThrow(() -> gerente.atender(s));
    }
    @Test
    void donoIsoladoProcessaQualquerTipo() {
        Dono dono = new Dono();
        Solicitacao s = solicitacao(Solicitacao.Tipo.PROBLEMA_GRAVE);
        assertDoesNotThrow(() -> dono.atender(s));
    }
    @Test
    void mediadorNaoLancaExcecaoAoNotificarCliente() {
        MediadorAtendimento mediador = new CentralAtendimento();
        assertDoesNotThrow(() -> mediador.notificarCliente(NOME_CLIENTE, "Mensagem teste"));
    }
    @Test
    void mediadorGarcomRegistraPedidoSemExcecao() {
        MediadorAtendimento mediador = new CentralAtendimento();
        Pedido pedido = criarPedidoComItem("Teste");
        assertDoesNotThrow(() -> mediador.garcomRegistrouPedido(pedido));
    }
    @Test
    void solicitacaoTipoReclamacaoEhReclamacao() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.RECLAMACAO);
        assertEquals(Solicitacao.Tipo.RECLAMACAO, s.getTipo());
    }
    @Test
    void solicitacaoTipoReembolsoEhReembolso() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.REEMBOLSO);
        assertEquals(Solicitacao.Tipo.REEMBOLSO, s.getTipo());
    }
    @Test
    void solicitacaoTipoProblemaGraveEhProblemaGrave() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.PROBLEMA_GRAVE);
        assertEquals(Solicitacao.Tipo.PROBLEMA_GRAVE, s.getTipo());
    }
    @Test
    void solicitacaoDescricaoNaoEhNula() {
        Solicitacao s = solicitacao(Solicitacao.Tipo.PEDIDO_SIMPLES);
        assertNotNull(s.getDescricao());
    }
    @Test
    void cadeiaCompletaProcessaTodosOsTipos() {
        Solicitacao s1 = solicitacao(Solicitacao.Tipo.PEDIDO_SIMPLES);
        Solicitacao s2 = solicitacao(Solicitacao.Tipo.RECLAMACAO);
        Solicitacao s3 = solicitacao(Solicitacao.Tipo.REEMBOLSO);
        Solicitacao s4 = solicitacao(Solicitacao.Tipo.PROBLEMA_GRAVE);
        assertDoesNotThrow(() -> cadeia.atender(s1));
        assertDoesNotThrow(() -> cadeia.atender(s2));
        assertDoesNotThrow(() -> cadeia.atender(s3));
        assertDoesNotThrow(() -> cadeia.atender(s4));
    }
    @Test
    void centralAtendimentoImplementaMediador() {
        assertInstanceOf(MediadorAtendimento.class, new CentralAtendimento());
    }
    @Test
    void clienteObservadorNaoLancaExcecaoAoAtualizar() {
        ClienteObservador obs = new ClienteObservador(NOME_CLIENTE);
        assertDoesNotThrow(() -> obs.atualizar(STATUS_PREPARANDO, 1));
    }
}
