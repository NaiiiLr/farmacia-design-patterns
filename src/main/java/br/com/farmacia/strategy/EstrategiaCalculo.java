package br.com.farmacia.strategy;

/**
 * Contrato do padrão Strategy para o cálculo do valor final de uma venda.
 *
 * Cada implementação representa uma regra de cálculo diferente. A classe Venda
 * conhece apenas esta interface e delega o cálculo para ela, então é possível
 * trocar ou adicionar regras sem alterar a Venda.
 */
public interface EstrategiaCalculo {

    /**
     * Calcula o valor final da venda a partir do subtotal dos itens.
     *
     * @param subtotal soma dos subtotais dos itens da venda (não pode ser negativo)
     * @return valor final a ser cobrado
     */
    double calcularTotal(double subtotal);

    /**
     * @return descrição curta da regra, usada na exibição da venda no console
     */
    String getDescricao();
}
