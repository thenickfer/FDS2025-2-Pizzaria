package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @PostMapping("/submit")
    @CrossOrigin("*")
    public PedidoPresenter submetePedido(@RequestBody Pedido Pedido) {

    }
}