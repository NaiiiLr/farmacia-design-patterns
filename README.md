# Farmácia - Design Patterns (Strategy + Builder)

Aplicação Java de console para um sistema simples de farmácia, desenvolvida para o Projeto Final. O projeto modela as entidades **Produto**, **Cliente** e **Venda**, aplicando os padrões de projeto **Strategy** e **Builder**.

## Descrição do Sistema

O sistema permite gerenciar um fluxo simples de vendas de farmácia pelo terminal, mantendo os dados em memória durante a execução:

- **Produto**: representa os itens disponíveis (`id`, `nome`, `preco`).
- **Cliente**: representa os compradores (`id`, `nome`, `cpf`).
- **Venda**: relaciona um `Cliente` aos itens comprados (`ItemVenda` com `Produto` e quantidade), usa o padrão **Builder** na montagem e o padrão **Strategy** no cálculo do valor final.

## Tecnologias e Pré-requisitos

- Java 21 (LTS)
- Apache Maven 3.9+ (ou Maven integrado ao IntelliJ IDEA)
- Git

## Estrutura do Projeto

```text
src/main/java/br/com/farmacia/
├── app/
│   └── Main.java
├── model/
│   ├── Produto.java
│   ├── Cliente.java
│   ├── ItemVenda.java
│   └── Venda.java
├── strategy/
│   ├── EstrategiaCalculo.java
│   ├── CalculoPadrao.java
│   └── CalculoComTaxaEntrega.java
└── builder/
    └── VendaBuilder.java
```

## Instruções de Execução

Clone o repositório:

```bash
git clone [https://github.com/NaiiiLr/farmacia-design-patterns.git](https://github.com/NaiiiLr/farmacia-design-patterns.git)
cd farmacia-design-patterns
```

Execute pelo terminal com Maven:

```bash
mvn compile exec:java
```

Ou abra o projeto no IntelliJ IDEA como projeto Maven e execute a classe `br.com.farmacia.app.Main`.

## Exemplos de Uso

```text
=== Sistema de Farmácia ===
1. Listar produtos
2. Cadastrar produto
3. Listar clientes
4. Cadastrar cliente
5. Registrar venda
6. Listar vendas realizadas
0. Sair
Escolha uma opção: 5
--- Registrar Nova Venda ---

--- Clientes Cadastrados ---
#1 - Ana Paula Souza (CPF: 111.222.333-44)
#2 - Carlos Eduardo Lima (CPF: 555.666.777-88)
#3 - Mariana Rocha (CPF: 999.888.777-66)
Informe o ID do cliente: 1

--- Produtos Disponíveis ---
#1 - Dipirona 500mg (R$ 8,50)
#2 - Paracetamol 750mg (R$ 12,00)
#3 - Vitamina C 1g (R$ 24,90)
#4 - Protetor Solar FPS 50 (R$ 65,00)
Informe o ID do produto: 1
Informe a quantidade: 2
Item adicionado com sucesso!
Deseja adicionar outro produto? (s/n): s

--- Produtos Disponíveis ---
#1 - Dipirona 500mg (R$ 8,50)
#2 - Paracetamol 750mg (R$ 12,00)
#3 - Vitamina C 1g (R$ 24,90)
#4 - Protetor Solar FPS 50 (R$ 65,00)
Informe o ID do produto: 3
Informe a quantidade: 1
Item adicionado com sucesso!
Deseja adicionar outro produto? (s/n): n

Escolha a forma de cálculo:
1. Cálculo Padrão (Balcão)
2. Entrega (taxa padrão de R$ 10,00)
3. Entrega (informar taxa personalizada)
Opção de cálculo: 2
Observação (opcional, pressione Enter para pular): Entregar na portaria

Venda registrada com sucesso!
----------------------------
Cliente: #1 - Ana Paula Souza (CPF: 111.222.333-44)
Itens:
- Dipirona 500mg | Quantidade: 2 | Subtotal: R$ 17,00
- Vitamina C 1g | Quantidade: 1 | Subtotal: R$ 24,90
Subtotal: R$ 41,90
Estratégia: Entrega (taxa de R$ 10,00)
Total: R$ 51,90
Observação: Entregar na portaria
----------------------------
```

## Patterns Utilizados

### Strategy (`br.com.farmacia.strategy`)

- **Problema resolvido:** evita condicionais (`if/else` ou `switch`) dentro de `Venda` para calcular o valor final, permitindo trocar a regra de cálculo sem alterar a classe de venda.
- **Classes envolvidas:**
    - `EstrategiaCalculo`: interface que define `calcularTotal(double subtotal)` e `getDescricao()`.
    - `CalculoPadrao`: venda no balcão, retorna o subtotal sem acréscimo.
    - `CalculoComTaxaEntrega`: soma uma taxa fixa de entrega ao subtotal. A taxa pode ser informada no construtor (não pode ser negativa) ou usar o valor padrão de R$ 10,00.
    - `Venda`: recebe uma `EstrategiaCalculo` e delega o cálculo do total para ela.
- **Como adicionar uma nova regra:** criar uma classe que implemente `EstrategiaCalculo` e passá-la para o `VendaBuilder` com `comEstrategia(...)`. Nenhuma outra classe precisa ser alterada.

```java
EstrategiaCalculo balcao = new CalculoPadrao();
balcao.calcularTotal(50.0);          // 50.0

EstrategiaCalculo entrega = new CalculoComTaxaEntrega();
entrega.calcularTotal(50.0);         // 60.0
entrega.getDescricao();              // "Entrega (taxa de R$ 10,00)"
```

### Builder (`br.com.farmacia.builder`)

- **Problema resolvido:** organiza a construção de uma `Venda` passo a passo, evitando um construtor longo e garantindo que o objeto seja criado somente quando os dados obrigatórios estiverem preenchidos.
- **Classes envolvidas:**
    - `ItemVenda`: relaciona um `Produto` a uma quantidade maior que zero e calcula o subtotal do item.
    - `Venda`: mantém o cliente, uma lista imutável de itens, a estratégia de cálculo e uma observação opcional.
    - `VendaBuilder`: oferece os métodos fluentes `comCliente`, `adicionarItem`, `comEstrategia`, `comObservacao` e `build`.
- **Validações realizadas:**
    - O produto não pode ser nulo.
    - A quantidade deve ser maior que zero.
    - A venda deve possuir um cliente.
    - A venda deve possuir pelo menos um item.
    - A venda deve possuir uma estratégia de cálculo.
- **Imutabilidade:** `Venda` cria uma cópia imutável da lista de itens recebida do Builder.

```java
Venda venda = new VendaBuilder()
        .comCliente(cliente)
        .adicionarItem(produto, 2)
        .comEstrategia(new CalculoPadrao())
        .comObservacao("Retirada no balcão")
        .build();

System.out.println(venda);
```

## Fluxo de Contribuição (Branches e Pull Requests)

A branch `main` é protegida e recebe alterações apenas via **Pull Request**.

### Branches das features (Equipe)

- `feature/strategy` ([Issue #1](https://github.com/NaiiiLr/farmacia-design-patterns/issues/1)): implementação do padrão Strategy (`EstrategiaCalculo`, `CalculoPadrao`, `CalculoComTaxaEntrega`)
- `feature/builder` ([Issue #2](https://github.com/NaiiiLr/farmacia-design-patterns/issues/2)): implementação de `ItemVenda`, `Venda` e `VendaBuilder`
- `feature/cli` ([Issue #3](https://github.com/NaiiiLr/farmacia-design-patterns/issues/3)): implementação do fluxo no console (`Main`)

```bash
git checkout main
git pull origin main
git checkout -b feature/<nome-da-feature>

# Após implementar e atualizar o README:
git add .
git commit -m "feat: descrição da alteração"
git push -u origin feature/<nome-da-feature>
```

Depois do push, abra um **Pull Request** no GitHub para a branch `main` referenciando a issue correspondente (ex.: `Closes #1`).

### Contribuição via Fork

Para equipes que forem estender o projeto ([Issue #4](https://github.com/NaiiiLr/farmacia-design-patterns/issues/4)):

1. Faça um **Fork** deste repositório no GitHub.
2. Clone o seu fork e crie uma branch para a extensão (ex.: `feature/extensao-farmacia`):

```bash
git clone [https://github.com/](https://github.com/)<SEU_USUARIO>/farmacia-design-patterns.git
cd farmacia-design-patterns
git checkout -b feature/extensao-farmacia
```

3. Faça os commits da equipe, envie a branch para o seu fork (`git push -u origin feature/extensao-farmacia`) e abra um **Pull Request** do fork para a branch `main` deste repositório referenciando `Closes #4`.

## Contribuições dos Integrantes

| Integrante     | Usuário GitHub  | Contribuição Principal |
|:---------------|:----------------|:-----------------------|
| **Ian**        | `@NaiiiLr`      | Setup do projeto (`pom.xml`, `.gitignore`, `.editorconfig`), `README.md` base e entidades `Produto` e `Cliente`. |
| **Cristoffer** | `@cristoffer21` | Implementação do padrão Strategy (`EstrategiaCalculo`, `CalculoPadrao`, `CalculoComTaxaEntrega`) e documentação do Strategy. |
| **Ryan**       | `@ryancandeu`   | Implementação do padrão Builder (`ItemVenda`, `Venda`, `VendaBuilder`) e documentação do Builder. |
| **Nathan**     | `@NathanHF`     | Implementação do fluxo no console (`Main`) e exemplos de uso no `README.md`. |

## Declaração de Uso de IA

- **Modelo utilizado:** Gemini 4 Neon.
- **Partes em que foi utilizada:** planejamento da estrutura do projeto, estrutura do `README.md`.


## Extensão  em andamento...