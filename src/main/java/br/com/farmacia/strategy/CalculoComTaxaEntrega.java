package br.com.farmacia.strategy;

import java.util.Locale;

/**
 * Estratégia de venda com entrega: soma uma taxa fixa de entrega ao subtotal.
 */
public class CalculoComTaxaEntrega implements EstrategiaCalculo {

    public static final double TAXA_PADRAO = 10.0;

    private final double taxaEntrega;

    /**
     * Cria a estratégia usando a taxa de entrega padrão (R$ 10,00).
     */
    public CalculoComTaxaEntrega() {
        this(TAXA_PADRAO);
    }

    /**
     * Cria a estratégia com uma taxa de entrega específica.
     *
     * @param taxaEntrega valor fixo somado ao subtotal (não pode ser negativo)
     */
    public CalculoComTaxaEntrega(double taxaEntrega) {
        if (taxaEntrega < 0) {
            throw new IllegalArgumentException("A taxa de entrega não pode ser negativa.");
        }
        this.taxaEntrega = taxaEntrega;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    @Override
    public double calcularTotal(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("O subtotal não pode ser negativo.");
        }
        return subtotal + taxaEntrega;
    }

    @Override
    public String getDescricao() {
        return String.format(Locale.forLanguageTag("pt-BR"), "Entrega (taxa de R$ %.2f)", taxaEntrega);
    }
}
