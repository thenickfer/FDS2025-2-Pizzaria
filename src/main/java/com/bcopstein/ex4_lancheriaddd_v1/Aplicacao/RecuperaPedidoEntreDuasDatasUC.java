package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidosEntreDuasDatasResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class RecuperaPedidoEntreDuasDatasUC {
    private PedidoService pedidoService;

    @Autowired
    public RecuperaPedidoEntreDuasDatasUC(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public PedidosEntreDuasDatasResponse run (String cpf, LocalDateTime ini, LocalDateTime fim){{
        List<Pedido> listaPedidos = pedidoService.porPeriodo(cpf, ini, fim);
        return new PedidosEntreDuasDatasResponse (listaPedidos);

    }
        
    }

}
