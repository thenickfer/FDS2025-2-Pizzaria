package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class PedidoPagamentoInvalidoException extends RuntimeException{

    public PedidoPagamentoInvalidoException(long idPedido, Pedido.Status status){
        super ("Pedido nao pode ser pago no estado atual. ID: " + idPedido + " Status: "+status.toString());
    }
    
}
