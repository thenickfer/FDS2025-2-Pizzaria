package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class PedidoCancelamentoInvalidoException extends RuntimeException {

    public PedidoCancelamentoInvalidoException (long idPedido, Pedido.Status status){
        super ("Pedido nao pode ser cancelado no estado atual. ID: " + idPedido + " Status: "+status.toString());
    }
    
}
