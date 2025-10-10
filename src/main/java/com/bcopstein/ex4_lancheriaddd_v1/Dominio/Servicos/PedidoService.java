package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;

    }

    public Pedido submetePedido(Pedido ped) {

        if (ped == null || ped.getItens() == null || ped.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido must have at least one item");
        }

        ped = pedidoRepository.submetePedido(ped);

        return ped;
    }

    public Pedido.Status getStatus(long id) {
        Pedido ped = pedidoRepository.getPedidoPorId(id);

        if (ped == null) {
            throw new IllegalArgumentException("pedido must exist");
        }

        return ped.getStatus();
    }

    public Boolean cancelarPedido(long id) {
        Boolean ped = pedidoRepository.cancelarPedido(id);

        if (!ped) {
            throw new IllegalArgumentException("pedido must exist");
        }

        return true;
    }

    // public Pedido mandaPedido(Pedido ped){
    // if (ped == null){
    // throw new IllegalArgumentException("pedido must exist");
    // }

    // ped = pagamentoService.processarPagamento(ped);
    // cozinhaService.chegadaDePedido(ped);
    // return ped;
    // }

}
