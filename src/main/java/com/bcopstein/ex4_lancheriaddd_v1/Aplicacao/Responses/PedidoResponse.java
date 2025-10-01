package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

public class PedidoResponse {
    private Pedido pedido;

    public PedidoResponse(Pedido pedido){
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }
    
}