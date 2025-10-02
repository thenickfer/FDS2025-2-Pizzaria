package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.SubmetePedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

public class SubmetePedidoUC {
    private PedidoService pedidoService;

    @Autowired
    public SubmetePedidoUC(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public SubmetePedidoResponse run(Pedido pedido) {
        pedido = pedidoService.submetePedido(pedido);
        return new SubmetePedidoResponse(pedido.getId(), pedido.getCliente(), pedido.getDataHoraPagamento(),
                pedido.getItens(), pedido.getStatus(), pedido.getValor(), pedido.getImpostos(), pedido.getDesconto(),
                pedido.getValorCobrado());
    }
}
