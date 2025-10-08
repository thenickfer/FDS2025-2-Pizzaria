package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;


@Service
public class PagamentoService {
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido processarPagamento(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
        }

        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new IllegalStateException("Somente pedidos aceitos podem ser pagos.");
        }

        pedido.setStatus(Pedido.Status.PAGO);

        return pedido;
    }
}

