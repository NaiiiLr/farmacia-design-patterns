package br.com.farmacia.model;

import java.util.Objects;

public class Produto {

    private final int id;
    private final String nome;
    private final double preco;

    public Produto(int id, String nome, double preco) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do produto deve ser maior que zero.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }

        this.id = id;
        this.nome = nome.trim();
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return String.format("#%d - %s (R$ %.2f)", id, nome, preco);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto produto)) {
            return false;
        }
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
