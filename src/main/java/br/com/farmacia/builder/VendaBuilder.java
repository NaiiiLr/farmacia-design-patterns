package br.com.farmacia.builder;

import br.com.farmacia.model.Cliente;
import br.com.farmacia.model.ItemVenda;
import br.com.farmacia.model.Produto;
import br.com.farmacia.model.Venda;
import br.com.farmacia.strategy.EstrategiaCalculo;

import java.util.ArrayList;
import java.util.List;

public class VendaBuilder {

    private Cliente cliente;
    private final List<ItemVenda> itens = new ArrayList<>();
    private EstrategiaCalculo estrategiaCalculo;
    private String observacao;

    public VendaBuilder comCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public VendaBuilder adicionarItem(
        Produto produto,
        int quantidade
    ) {
        itens.add(new ItemVenda(produto, quantidade));
        return this;
    }

    public VendaBuilder comEstrategia(
        EstrategiaCalculo estrategiaCalculo
    ) {
        this.estrategiaCalculo = estrategiaCalculo;
        return this;
    }

    public VendaBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public Venda build() {
        if (cliente == null) {
            throw new IllegalStateException(
                "Não é possível criar uma venda sem cliente."
            );
        }

        if (itens.isEmpty()) {
            throw new IllegalStateException(
                "Não é possível criar uma venda sem itens."
            );
        }

        if (estrategiaCalculo == null) {
            throw new IllegalStateException(
                "Não é possível criar uma venda sem estratégia de cálculo."
            );
        }

        return new Venda(
            cliente,
            itens,
            estrategiaCalculo,
            observacao
        );
    }
}
