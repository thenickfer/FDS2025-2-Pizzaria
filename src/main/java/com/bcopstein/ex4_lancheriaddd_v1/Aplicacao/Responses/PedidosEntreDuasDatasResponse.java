package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class PedidosEntreDuasDatasResponse {
    private List<Pedido> pedidos;

    public PedidosEntreDuasDatasResponse(List<Pedido> pedidos){
        this.pedidos = pedidos;
    }

    public List<Pedido> getPedidos(){return this.pedidos;}
}
