package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class ItemPedido {
    private Produto item;
    private long quantidade;
    private long id;

    public ItemPedido(long id, Produto item, long quantidade) {
        this.id = id;
        this.item = item;
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public Produto getItem() {
        return item;
    }

    public long getQuantidade() {
        return quantidade;
    }
}
