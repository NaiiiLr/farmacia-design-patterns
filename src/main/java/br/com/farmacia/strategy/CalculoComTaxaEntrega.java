package br.com.farmacia.strategy;

import java.util.Locale;

public class CalculoComTaxaEntrega implements EstrategiaCalculo {

    public static final double TAXA_PADRAO = 10.0;

    private final double taxaEntrega;

    public CalculoComTaxaEntrega() {
        this(TAXA_PADRAO);
    }

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
