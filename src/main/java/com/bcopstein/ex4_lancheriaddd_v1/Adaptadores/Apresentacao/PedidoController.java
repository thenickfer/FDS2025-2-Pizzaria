package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters.StatusPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmetePedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmetePedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.StatusPedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.SubmetePedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaStatusPedidoUC;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private SubmetePedidoUC submetePedidoUC;
    private RecuperaStatusPedidoUC recuperaStatusPedidoUC;

    public PedidoController(SubmetePedidoUC submetePedidoUC, RecuperaStatusPedidoUC recuperaStatusPedidoUC) {
        this.submetePedidoUC = submetePedidoUC;
        this.recuperaStatusPedidoUC = recuperaStatusPedidoUC;
    }

    @GetMapping("/status/{id}")
    @CrossOrigin("*")
    public StatusPresenter getStatus(@PathVariable(value = "id") long id) {
        StatusPedidoResponse statusResponse = recuperaStatusPedidoUC.run(id);
        return new StatusPresenter(statusResponse.status());
    }

    @PostMapping("/submit")
    @CrossOrigin("*")
    public PedidoPresenter submetePedido(@RequestBody PedidoRequest pedido) {

        SubmetePedidoRequest applicationRequest = new SubmetePedidoRequest(
                pedido.getCliente(),
                pedido.getDataHoraPagamento(),
                pedido.getItens().stream()
                        .map(item -> new SubmetePedidoRequest.ItemRequest(item.getProdutoId(), item.getQuantidade()))
                        .toList());

        SubmetePedidoResponse pedidoResponse = submetePedidoUC.run(applicationRequest);
        PedidoPresenter pedidoPresenter = new PedidoPresenter(
                pedidoResponse.getId(),
                pedidoResponse.getCliente(),
                pedidoResponse.getDataHoraPagamento(),
                pedidoResponse.getItens(),
                pedidoResponse.getStatus(),
                pedidoResponse.getValor(),
                pedidoResponse.getImpostos(),
                pedidoResponse.getDesconto(),
                pedidoResponse.getValorCobrado());
        return pedidoPresenter;
    }

}