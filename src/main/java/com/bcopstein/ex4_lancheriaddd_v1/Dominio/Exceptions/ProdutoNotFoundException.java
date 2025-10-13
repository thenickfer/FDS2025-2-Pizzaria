package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

public class ProdutoNotFoundException extends RuntimeException {
    
    public ProdutoNotFoundException (long id){
        super ("Produto nao encontrado. ID: "+id);}
}

