package com.sabornabrasa.core;

import com.sabornabrasa.atendimento.*;
import com.sabornabrasa.cardapio.*;
import com.sabornabrasa.cozinha.*;
import com.sabornabrasa.entrega.*;
import com.sabornabrasa.pagamento.*;
import com.sabornabrasa.pedido.*;
import com.sabornabrasa.relatorio.*;
import com.sabornabrasa.sistema.ConfiguracaoSistema;
public class HamburgueriaFacade {
    private final ConfiguracaoSistema config    = ConfiguracaoSistema.getInstancia();
    private final HamburguerDirector director   = new HamburguerDirector();
    private final ProcessadorPagamento pagamento = new ProcessadorPagamento();
    private final ServicoEntrega entrega        = new EntregaAdapter(new SistemaEntregaLegado());
    private final MediadorAtendimento mediador  = new CentralAtendimento();
    private final FilaComandos filaComandos     = new FilaComandos();
    private final HistoricoPedido historico     = new HistoricoPedido();
    private final PedidoProxy proxy             = new PedidoProxy(filaComandos, historico);
    private final CalculadorPrecoVisitor calcPreco = new CalculadorPrecoVisitor();
    public void realizarPedidoTradicional(String nomeCliente, String endereco) {
        Hamburguer h      = director.criarTradicional();
        ItemCardapio item = new BaconDecorator(new QueijoExtraDecorator(h));
        Ingrediente agua  = IngredienteCache.getIngrediente("Agua", false);
        Combo combo = new Combo("Combo " + nomeCliente);
        combo.adicionar(item);
        combo.adicionar(new Produto("Refrigerante", 8.0, agua));
        Pedido pedido = new Pedido(nomeCliente);
        pedido.adicionarItem(combo);
        pedido.assinar(new ClienteObservador(nomeCliente));
        pagamento.setEstrategiaDesconto(new DescontoClienteNovo());
        pagamento.processar(pedido);
        proxy.processarAvancar(pedido);
        new PreparoTradicional().preparar(pedido);
        mediador.cozinhaFinalizouPedido(pedido);
        entrega.realizarEntrega(pedido, endereco);
        calcPreco.resetar();
        combo.aceitar(calcPreco);
    }
    public void exibirCardapio(boolean vegano) {
        CardapioFactory factory = vegano ? new CardapioVeganoFactory() : new CardapioTradicionalFactory();
        Menu menu = new Menu(vegano ? "Vegano" : "Tradicional");
        menu.adicionar(factory.criarHamburguer());
        menu.adicionar(factory.criarBebida());
        menu.adicionar(factory.criarBatata());
    }
    public void registrarSolicitacao(Solicitacao solicitacao) {
        Atendente atendente = new Atendente();
        Gerente   gerente   = new Gerente();
        Dono      dono      = new Dono();
        atendente.setProximo(gerente).setProximo(dono);
        atendente.atender(solicitacao);
    }
    public String rastrearPedido(int numero) { return entrega.rastrearPedido(numero); }
    public HistoricoPedido getHistorico() { return historico; }
}
