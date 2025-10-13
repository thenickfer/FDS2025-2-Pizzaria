package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PagamentoErroException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoNotFoundException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoPagamentoInvalidoException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CozinhaService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PagamentoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class PagaPedidoUC {
    private PedidoService pedidoService;
    private CozinhaService cozinhaService;
    private PagamentoService pagamentoService;

    public PagaPedidoUC(PedidoService pedidoService, CozinhaService cozinhaService, PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
        this.pedidoService = pedidoService;
        this.cozinhaService = cozinhaService;
    }

    public Pedido run(long idPedido) {
        if (!pedidoService.exists(idPedido))
            throw new PedidoNotFoundException(idPedido);

        Pedido ped = pedidoService.getPedido(idPedido);
        if (ped.getStatus() != Pedido.Status.APROVADO)
            throw new PedidoPagamentoInvalidoException(idPedido, ped.getStatus());

        ped = pagamentoService.processarPagamento(ped);
        if (ped.getStatus() != Pedido.Status.PAGO) {
            throw new PagamentoErroException(idPedido);
        }
        cozinhaService.chegadaDePedido(ped);
        return ped;

    }
}
