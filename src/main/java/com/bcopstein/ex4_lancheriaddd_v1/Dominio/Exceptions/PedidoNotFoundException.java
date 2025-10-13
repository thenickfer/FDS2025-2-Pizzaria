package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

public class PedidoNotFoundException extends RuntimeException{
    
    public PedidoNotFoundException (long id){
        super ("Pedido nao encontrado. ID: "+id);
    }
   
}
