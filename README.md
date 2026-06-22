# Sabor na Brasa

Sistema de gestão de uma hamburgueria implementado em Java, demonstrando os **23 padrões de projeto GoF** (Gang of Four) aplicados em um contexto de negócio real.

## Sobre o projeto

O **Sabor na Brasa** é uma hamburgueria fictícia cujo sistema cobre todo o fluxo de operação: do cardápio à entrega, passando por pedidos, atendimento, cozinha, pagamento e relatórios. Cada subsistema foi modelado aplicando padrões de projeto adequados às suas responsabilidades.

## Tecnologias

- **Java 17**
- **Maven** para gerenciamento de dependências
- **JUnit 5** para testes unitários
- **UTF-8** como encoding padrão

## Estrutura do projeto

```
Sabor na Brasa/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/sabornabrasa/
    │   ├── sistema/        Configuração global (Singleton)
    │   ├── core/           Ponto de entrada (Facade)
    │   ├── cardapio/       Itens, ingredientes, combos
    │   ├── pedido/         Pedidos, estados, comandos, histórico
    │   ├── atendimento/    Cadeia de atendimento, mediador
    │   ├── cozinha/        Rotinas de preparo
    │   ├── pagamento/      Estratégias de desconto
    │   ├── entrega/        Adaptador para sistema legado
    │   └── relatorio/      Visitantes para cálculos
    └── test/java/com/sabornabrasa/
        ├── CardapioTest.java                (62 testes)
        ├── PedidoTest.java                  (56 testes)
        ├── CozinhaPagamentoEntregaTest.java (43 testes)
        ├── AtendimentoTest.java             (24 testes)
        └── FacadeIntegracaoTest.java        (16 testes)
```

## Os 23 padrões GoF aplicados

### Padrões Criacionais

| Padrão | Classes principais | Onde aparece |
|---|---|---|
| **Singleton** | `ConfiguracaoSistema` | Configuração global única do sistema |
| **Factory Method** | `LancheFactory`, `XBurgerFactory`, `XSaladaFactory` | Criação de lanches específicos |
| **Abstract Factory** | `CardapioFactory`, `CardapioTradicionalFactory`, `CardapioVeganoFactory` | Famílias de produtos (tradicional/vegano) |
| **Builder** | `HamburguerBuilder`, `HamburguerDirector` | Montagem passo a passo de hambúrgueres |
| **Prototype** | `Hamburguer.clone()` | Clonagem de hambúrgueres existentes |

### Padrões Estruturais

| Padrão | Classes principais | Onde aparece |
|---|---|---|
| **Adapter** | `EntregaAdapter`, `SistemaEntregaLegado` | Integração com sistema legado de entrega |
| **Bridge** | `Ingrediente`, `IngredienteTradicional`, `IngredienteVegano` | Desacopla produto do tipo de ingrediente |
| **Composite** | `ItemCardapio`, `Produto`, `Combo` | Combos formados por vários itens |
| **Decorator** | `HamburguerDecorator`, `BaconDecorator`, `QueijoExtraDecorator` | Acréscimos no hambúrguer |
| **Facade** | `HamburgueriaFacade` | Ponto único de entrada do sistema |
| **Flyweight** | `IngredienteCache` | Compartilha instâncias de ingredientes |
| **Proxy** | `PedidoProxy` | Controla acesso ao processamento de pedidos |

### Padrões Comportamentais

| Padrão | Classes principais | Onde aparece |
|---|---|---|
| **Chain of Responsibility** | `ManipuladorAtendimento`, `Atendente`, `Gerente`, `Dono` | Escalada de atendimento |
| **Command** | `ComandoPedido`, `AvancarPedidoCommand`, `FilaComandos` | Ações sobre pedido com desfazer |
| **Interpreter** | `InterpretadorPedido` | Interpreta pedidos em texto livre |
| **Iterator** | `Menu` | Percorre itens do cardápio |
| **Mediator** | `MediadorAtendimento`, `CentralAtendimento` | Coordena cliente, garçom e cozinha |
| **Memento** | `PedidoMemento`, `HistoricoPedido` | Snapshots para desfazer ações |
| **Observer** | `ObservadorPedido`, `ClienteObservador`, `CozinhaObservador` | Notificações de mudança de estado |
| **State** | `EstadoPedido` + 5 estados concretos | Ciclo de vida do pedido |
| **Strategy** | `DescontoStrategy`, `SemDesconto`, `DescontoClienteNovo`, `DescontoClienteVip` | Políticas de desconto trocáveis |
| **Template Method** | `PreparoPedido`, `PreparoTradicional`, `PreparoVegano` | Sequência fixa de preparo com etapas variáveis |
| **Visitor** | `VisitorRelatorio`, `CalculadorPrecoVisitor`, `RelatorioImpostoVisitor` | Cálculos sobre a hierarquia do cardápio |

## Como compilar e rodar

### Compilar
```bash
mvn clean compile
```

### Rodar os testes
```bash
mvn test
```

### Empacotar
```bash
mvn package
```

## Testes

O projeto possui **201 testes unitários** distribuídos em 5 arquivos, cobrindo todos os padrões implementados.

| Arquivo | Testes |
|---|---|
| `CardapioTest` | 62 |
| `PedidoTest` | 56 |
| `CozinhaPagamentoEntregaTest` | 43 |
| `AtendimentoTest` | 24 |
| `FacadeIntegracaoTest` | 16 |
| **Total** | **201** |

### Padrões adotados nos testes

- Cada teste verifica **uma única unidade de comportamento** com 1 assertion
- **Zero estruturas de controle** (sem if, else, for, while) nos testes
- **Zero números mortos** — todos os valores são constantes nomeadas
- **Zero prints** durante a execução
- Uso de `@BeforeEach` para preparar o cenário de teste

## Diagrama de classes

O projeto inclui um diagrama no formato draw.io (`diagrama-sabor-na-brasa.drawio`) organizado em **agrupamentos por padrão GoF**, contendo:

- **23 grupos de padrões** com swimlanes coloridos
- **56 classes e interfaces** com seus estereótipos
- **68 conexões** entre os padrões
- **Legenda das setas** no canto superior direito

### Legenda das conexões

| Cor | Tipo | Significado |
|---|---|---|
| 🟢 Verde — triângulo vazio | `implements` | Implementação de interface |
| 🔵 Azul — triângulo cheio | `extends` | Herança |
| 🟠 Laranja — losango | composição | Tem-um / contém |
| ⚪ Cinza tracejada | dependência | Usa / cria |
| 🟣 Roxa tracejada | associação | Referência |

Para abrir o diagrama, acesse [app.diagrams.net](https://app.diagrams.net) e use **File → Open from → Device**.

## Princípios de qualidade do código

- **Sem comentários no código** — nomes autoexplicativos
- **Sem classes órfãs** — todas testadas e referenciadas
- **Sem prints no código de produção** — apenas onde necessário ao fluxo de negócio
- **Nomes técnicos dos padrões** preservados (`HamburguerBuilder`, `PedidoProxy`, `ProcessadorPagamento`, etc.)
- **Encoding UTF-8** em todos os arquivos
- **Java 17** com recursos modernos (records, switch expressions, var)

## Autores

Projeto desenvolvido como demonstração prática dos 23 padrões de projeto GoF aplicados a um cenário de negócio real.