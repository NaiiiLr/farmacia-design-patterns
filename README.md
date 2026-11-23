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
git clone https://github.com/NaiiiLr/farmacia-design-patterns.git
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
2. Listar clientes
3. Registrar venda
4. Listar vendas realizadas
0. Sair
```

## Patterns Utilizados

### Strategy (`br.com.farmacia.strategy`)

- **Problema resolvido:** evita condicionais (`if/else` ou `switch`) dentro de `Venda` para calcular o valor final, permitindo trocar a regra de cálculo sem alterar a classe de venda.
- **Classes envolvidas:**
  - `EstrategiaCalculo`: interface que define `calcularTotal(double subtotal)` e `getDescricao()`.
  - `CalculoPadrao`: retorna o valor do subtotal sem alterações.
  - `CalculoComTaxaEntrega`: soma uma taxa fixa de entrega ao subtotal.
  - `Venda`: recebe uma `EstrategiaCalculo` e delega o cálculo do total para ela.

### Builder (`br.com.farmacia.builder`)

- **Problema resolvido:** organiza a montagem de objetos `Venda` passo a passo (cliente, itens, estratégia de cálculo e observação opcional), evitando construtores longos e concentrando as validações antes de criar a instância.
- **Classes envolvidas:**
  - `VendaBuilder`: disponibiliza os métodos `comCliente`, `adicionarItem`, `comEstrategia`, `comObservacao` e `build`.
  - `Venda`: objeto final criado pelo `VendaBuilder`.

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
git clone https://github.com/<SEU_USUARIO>/farmacia-design-patterns.git
cd farmacia-design-patterns
git checkout -b feature/extensao-farmacia
```

3. Faça os commits da equipe, envie a branch para o seu fork (`git push -u origin feature/extensao-farmacia`) e abra um **Pull Request** do fork para a branch `main` deste repositório referenciando `Closes #4`.

## Contribuições dos Integrantes

| Integrante | Usuário GitHub | Contribuição Principal |
| :--- | :--- | :--- |
| **Ian** | `@NaiiiLr` | Setup do projeto (`pom.xml`, `.gitignore`, `.editorconfig`), `README.md` base e entidades `Produto` e `Cliente`. |
| **Cristoffer** | `@cristoffer21` | Implementação do padrão Strategy (`EstrategiaCalculo`, `CalculoPadrao`, `CalculoComTaxaEntrega`) e documentação do Strategy. |
| **[Nome Integrante 3]** | `@usuario3` | Implementação do padrão Builder (`ItemVenda`, `Venda`, `VendaBuilder`) e documentação do Builder. |
| **[Nome Integrante 4]** | `@usuario4` | Implementação do fluxo no console (`Main`) e exemplos de uso no `README.md`. |

## Declaração de Uso de IA

- **Modelo utilizado:** Gemini 4 Neon.
- **Partes em que foi utilizada:** planejamento da estrutura do projeto, estrutura do `README.md`.
