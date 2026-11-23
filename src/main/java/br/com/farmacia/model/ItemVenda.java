package br.com.farmacia.model;

import java.util.Locale;

public class ItemVenda {

    private final Produto produto;
    private final int quantidade;

    public ItemVenda(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto é obrigatório.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "A quantidade deve ser maior que zero."
            );
        }

        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format(
            Locale.forLanguageTag("pt-BR"),
            "%s | Quantidade: %d | Subtotal: R$ %.2f",
            produto.getNome(),
            quantidade,
            calcularSubtotal()
        );
    }
}
