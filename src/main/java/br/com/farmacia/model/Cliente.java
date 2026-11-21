package br.com.farmacia.model;

import java.util.Objects;

public class Cliente {

    private final int id;
    private final String nome;
    private final String cpf;

    public Cliente(int id, String nome, String cpf) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do cliente deve ser maior que zero.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório.");
        }

        this.id = id;
        this.nome = nome.trim();
        this.cpf = cpf.trim();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return String.format("#%d - %s (CPF: %s)", id, nome, cpf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente cliente)) {
            return false;
        }
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
