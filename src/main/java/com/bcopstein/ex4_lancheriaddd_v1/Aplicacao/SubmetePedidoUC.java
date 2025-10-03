package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.SubmetePedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.SubmetePedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ProdutoService;

@Component
public class SubmetePedidoUC {
    private PedidoService pedidoService;
    private ProdutoService produtosService;

    @Autowired
    public SubmetePedidoUC(PedidoService pedidoService, ProdutoService produtosService) {
        this.pedidoService = pedidoService;
        this.produtosService = produtosService;
    }

    public SubmetePedidoResponse run(SubmetePedidoRequest request) {

        Map<Long, Produto> map = produtosService.getMapping();

        List<ItemPedido> itens = request.getItens().stream()
                .map(itemRequest -> {
                    Produto produto = map.get(itemRequest.getProdutoId());
                    if (produto == null) {
                        throw new IllegalArgumentException(
                                "Produto nao encontrado com ID: " + itemRequest.getProdutoId());
                    }
                    return new ItemPedido(produto, itemRequest.getQuantidade());
                })
                .toList();

        Pedido pedido = new Pedido(
                0,
                request.getCliente(),
                request.getDataHoraPagamento(),
                itens,
                Pedido.Status.NOVO,
                request.getValor(),
                request.getImpostos(),
                request.getDesconto(),
                0.0);

        // Submit the pedido through the service
        pedido = pedidoService.submetePedido(pedido);

        return new SubmetePedidoResponse(
                pedido.getId(),
                pedido.getCliente(),
                pedido.getDataHoraPagamento(),
                pedido.getItens(),
                pedido.getStatus(),
                pedido.getValor(),
                pedido.getImpostos(),
                pedido.getDesconto(),
                pedido.getValorCobrado());
    }
}
