package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CozinhaService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

public class SubmetePedidoUC {
    private CozinhaService cozinhaService;
    private PedidoService pedidoService;

    @Autowired
    public SubmetePedidoUC(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    public PedidoResponse run(Pedido ped){
        Pedido pedido = pedidoService.submetePedido(ped);

        return new PedidoResponse(pedido);
    }
}
