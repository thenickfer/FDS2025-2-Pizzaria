package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

public class CardapioNotFoundException extends RuntimeException{

    public CardapioNotFoundException(long idCardapio){
        super ("Cardapio inexistente. ID: "+idCardapio);
    }
    
}
