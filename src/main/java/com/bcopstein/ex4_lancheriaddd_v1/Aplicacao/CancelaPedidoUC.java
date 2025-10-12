package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class CancelaPedidoUC {
    private PedidoService pedidoService;

    @Autowired
    public CancelaPedidoUC(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public boolean run(long idPedido) {
        if (!pedidoService.exists(idPedido)) {
            throw new RuntimeException("Pedido nao existe: " + idPedido);
        }

        Pedido.Status status = pedidoService.getStatus(idPedido);
        if (status != Pedido.Status.APROVADO) {
            throw new RuntimeException("Pedido nao pode ser cancelado no estado atual " + idPedido);
        }

        return pedidoService.cancelarPedido(idPedido);
    }
}
