package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ObjetosDB;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "ingredientes")
public class IngredienteBD {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String descricao;

    protected IngredienteBD(){}

    public IngredienteBD (String descricao){
        this.descricao = descricao;
    }

    public IngredienteBD (long id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId (){return this.id;}
    public String getDescricao (){return this.descricao;}
    public static Ingrediente fromIngredienteBD (IngredienteBD ibd){
        return new Ingrediente(ibd.getId(), ibd.getDescricao());
    }
    public static IngredienteBD toIngredienteBD (Ingrediente i){
        return new IngredienteBD(i.getId(),i.getDescricao());
    }

}
