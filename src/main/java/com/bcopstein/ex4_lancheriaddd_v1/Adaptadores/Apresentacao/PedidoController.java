package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmetePedidoUC;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private SubmetePedidoUC submetePedidoUC;

    public PedidoController(SubmetePedidoUC submetePedidoUC) {
        this.submetePedidoUC = submetePedidoUC;
    }

    @PostMapping("/submit")
    @CrossOrigin("*")
    public PedidoPresenter submetePedido(@RequestBody PedidoRequest pedido) {
        PedidoResponse pedidoResponse = submetePedidoUC.run(pedido);
        PedidoPresenter pedidoPresenter = new PedidoPresenter()
    }
}