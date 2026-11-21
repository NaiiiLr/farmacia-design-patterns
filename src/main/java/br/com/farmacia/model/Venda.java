package br.com.farmacia.model;

/**
 * TODO (Integrante 3 - Builder e Venda):
 * Representar uma venda concluída, construída exclusivamente pelo VendaBuilder.
 *
 * Atributos esperados:
 * - Cliente cliente
 * - List<ItemVenda> itens (retornar cópia/lista imutável no getter)
 * - EstrategiaCalculo estrategiaCalculo (do pacote br.com.farmacia.strategy)
 * - String observacao (opcional)
 *
 * Métodos esperados:
 * - Getters dos atributos
 * - calcularSubtotal(): somar o subtotal de todos os itens
 * - calcularTotal(): delegar para estrategiaCalculo.calcularTotal(calcularSubtotal())
 * - toString() ou método de resumo para exibição no console
 */
public class Venda {
    // TODO: implementar atributos, construtor (usado pelo VendaBuilder), getters e cálculos
}
