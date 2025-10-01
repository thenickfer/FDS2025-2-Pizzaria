package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class PedidoService {
    private PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido submetePedido(Pedido ped) {
        return pedidoRepository.submetePedido(ped);
    }

    public Pedido.Status geStatus(long id) {
        return pedidoRepository.getStatus(id);
    }

    public Boolean cancelarPedido(long id) {
        return pedidoRepository.cancelarPedido(id);
    }
}
