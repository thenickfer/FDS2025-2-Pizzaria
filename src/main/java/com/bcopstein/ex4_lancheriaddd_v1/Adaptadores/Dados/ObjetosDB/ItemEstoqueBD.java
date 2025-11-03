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
    private long id;
    @OneToOne(cascade = CascadeType.REFRESH)
    private IngredienteBD ingrediente; //arrumar pra retornar objeto ingrediente

    private int quantidade;

}
