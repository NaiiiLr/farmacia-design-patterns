package br.com.farmacia.model;

import br.com.farmacia.strategy.EstrategiaCalculo;

import java.util.List;
import java.util.Locale;

public class Venda {

    private final Cliente cliente;
    private final List<ItemVenda> itens;
    private final EstrategiaCalculo estrategiaCalculo;
    private final String observacao;

    public Venda(
        Cliente cliente,
        List<ItemVenda> itens,
        EstrategiaCalculo estrategiaCalculo,
        String observacao
    ) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                "O cliente é obrigatório."
            );
        }

        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException(
                "A venda deve possuir pelo menos um item."
            );
        }

        if (estrategiaCalculo == null) {
            throw new IllegalArgumentException(
                "A estratégia de cálculo é obrigatória."
            );
        }

        this.cliente = cliente;
        this.itens = List.copyOf(itens);
        this.estrategiaCalculo = estrategiaCalculo;
        this.observacao = observacao == null
            ? ""
            : observacao.trim();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public EstrategiaCalculo getEstrategiaCalculo() {
        return estrategiaCalculo;
    }

    public String getObservacao() {
        return observacao;
    }

    public double calcularSubtotal() {
        return itens.stream()
            .mapToDouble(ItemVenda::calcularSubtotal)
            .sum();
    }

    public double calcularTotal() {
        return estrategiaCalculo.calcularTotal(
            calcularSubtotal()
        );
    }

    @Override
    public String toString() {
        Locale locale = Locale.forLanguageTag("pt-BR");
        String quebraDeLinha = System.lineSeparator();
        StringBuilder resumo = new StringBuilder();

        resumo.append("Cliente: ")
            .append(cliente)
            .append(quebraDeLinha);

        resumo.append("Itens:")
            .append(quebraDeLinha);

        for (ItemVenda item : itens) {
            resumo.append("- ")
                .append(item)
                .append(quebraDeLinha);
        }

        resumo.append(String.format(
            locale,
            "Subtotal: R$ %.2f%n",
            calcularSubtotal()
        ));

        resumo.append("Estratégia: ")
            .append(estrategiaCalculo.getDescricao())
            .append(quebraDeLinha);

        resumo.append(String.format(
            locale,
            "Total: R$ %.2f",
            calcularTotal()
        ));

        if (!observacao.isBlank()) {
            resumo.append(quebraDeLinha)
                .append("Observação: ")
                .append(observacao);
        }

        return resumo.toString();
    }
}
