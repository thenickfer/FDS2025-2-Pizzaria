package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.StatusPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class RecuperaStatusPedidoUC {
    private PedidoService pedidoService;

    @Autowired
    public RecuperaStatusPedidoUC(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    public StatusPedidoResponse run (long idPedido){
        Pedido.Status pedidoStatus = pedidoService.getStatus(idPedido);
        return new StatusPedidoResponse(pedidoStatus);
    }
}