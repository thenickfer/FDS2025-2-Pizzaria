package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

public class ItemEstoque {
    private Ingrediente ingrediente;
    private int quantidade;
    private long id;

    public ItemEstoque(long id, Ingrediente ingrediente, int quantidade) {
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
        this.id = id;
    }

    public long getId(){return this.id;}
    public Ingrediente getIngrediente() { return ingrediente; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
