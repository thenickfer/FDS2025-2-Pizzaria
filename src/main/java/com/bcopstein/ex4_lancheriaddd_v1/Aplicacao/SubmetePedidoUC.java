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
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ClienteService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.EstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ProdutoService;

@Component
public class SubmetePedidoUC {
    private PedidoService pedidoService;
    private ProdutoService produtosService;
    private ClienteService clienteService;
    private ImpostoService impostoService;
    private DescontoService descontoService;
    private EstoqueService estoqueService;

    @Autowired
    public SubmetePedidoUC(PedidoService pedidoService, ProdutoService produtosService, ClienteService clienteService,
            ImpostoService impostoService, DescontoService descontoService, EstoqueService estoqueService) {
        this.pedidoService = pedidoService;
        this.produtosService = produtosService;
        this.clienteService = clienteService;
        this.descontoService = descontoService;
        this.impostoService = impostoService;
        this.estoqueService = estoqueService;
    }

    public SubmetePedidoResponse run(SubmetePedidoRequest request) {

        if (clienteService.getByCpf(request.getCliente().getCpf()) == null) {
            throw new IllegalArgumentException("Cliente inexistente");
        }

        Map<Long, Produto> map = produtosService.getMapping();

        List<ItemPedido> itens = request.getItens().stream()
                .map(itemRequest -> {
                    Produto produto = map.get(itemRequest.getProdutoId());
                    if (produto == null) {
                        throw new IllegalArgumentException(
                                "Produto nao encontrado com ID: " + itemRequest.getProdutoId());
                    }
                    return new ItemPedido(0, produto, itemRequest.getQuantidade());
                })
                .toList();

        Pedido pedido = new Pedido(
                0,
                request.getCliente(),
                request.getDataHoraPagamento(),
                itens,
                Pedido.Status.NOVO,
                0,
                0,
                0,
                0.0);

        pedido = estoqueService.avaliarPedido(pedido);

        if (pedido.getStatus() == Pedido.Status.APROVADO) {
            double val = pedido.getItens().stream().map(i -> i.getItem()).mapToDouble(p -> p.getPreco()).sum();
            pedido.setValor(val);
            val -= descontoService.calcularDesconto(pedido);
            val += impostoService.calcularImposto(pedido);
            pedido.setValorCobrado(val);
        }

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
