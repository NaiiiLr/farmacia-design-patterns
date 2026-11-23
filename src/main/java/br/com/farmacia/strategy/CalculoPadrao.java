package br.com.farmacia.strategy;

public class CalculoPadrao implements EstrategiaCalculo {

    @Override
    public double calcularTotal(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("O subtotal não pode ser negativo.");
        }
        return subtotal;
    }

    @Override
    public String getDescricao() {
        return "Cálculo Padrão (Balcão)";
    }
}
