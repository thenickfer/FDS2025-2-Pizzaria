package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ObjetosDB;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "itensEstoque")
public class ItemEstoqueBD {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) //auto incrementa
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH) //indica relacao com ingrediente   
    private IngredienteBD ingrediente; //arrumar pra retornar objeto ingrediente

    private int quantidade;

    public ItemEstoqueBD(){}

    public ItemEstoqueBD(Long id, IngredienteBD ing, int qtd){
        this.id = id;
        this.ingrediente = ing;
        this.quantidade = qtd;
    }

    public Long getId (){return this.id;}

    public IngredienteBD getIngrediente(){return this.ingrediente;}

    public int getQuantidade (){return this.quantidade;}

    public static ItemEstoque fromItemEstoqueBD(ItemEstoqueBD ieb){
        return new ItemEstoque(ieb.getId,IngredienteBD.fromIngredienteBD(ieb.getIngrediente()),ieb.getQuantidade);
    }

    public static ItemEstoqueBD toItemEstoqueBD (ItemEstoque i){
        return new ItemEstoqueBD(i.getId,IngredienteBD.toIngredienteBD(i.getIngrediente),i.getQuantidade);
    }

}
