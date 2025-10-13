package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

public class PagamentoErroException extends RuntimeException{

    public PagamentoErroException (long idPedido){
        super ("Erro no pagamento do pedido: " + idPedido);
    }
    
}
