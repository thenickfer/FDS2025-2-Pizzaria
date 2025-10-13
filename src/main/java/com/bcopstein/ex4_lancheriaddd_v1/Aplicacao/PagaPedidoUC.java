package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CozinhaService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.EntregaService;
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
            throw new RuntimeException("Pedido nao existe: " + idPedido);

        Pedido ped = pedidoService.getPedido(idPedido);
        if (ped.getStatus() != Pedido.Status.APROVADO)
            throw new RuntimeException("Pedido nao pode ser pago no status: " + ped.getStatus().name());

        ped = pagamentoService.processarPagamento(ped);
        if (ped.getStatus() != Pedido.Status.PAGO) {
            throw new RuntimeException("Erro no pagamento do pedido: " + idPedido);
        }
        cozinhaService.chegadaDePedido(ped);
        return ped;

    }
}
