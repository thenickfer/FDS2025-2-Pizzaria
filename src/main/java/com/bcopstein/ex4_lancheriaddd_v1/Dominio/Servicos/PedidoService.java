package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.time.LocalDateTime;
import java.util.List;

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

    public boolean cancelarPedido(long id) {
        Boolean ped = pedidoRepository.cancelarPedido(id);

        if (!ped) {
            throw new IllegalArgumentException("pedido must exist");
        }

        return true;
    }

    public boolean exists(long id) {
        return pedidoRepository.getPedidoPorId(id) != null;
    }

    public Pedido getPedido(long id) {
        Pedido ped = pedidoRepository.getPedidoPorId(id);
        if (ped == null) {
            throw new RuntimeException("Pedido nao existe: " + id);
        }
        return ped;
    }

    public List<Pedido> porPeriodo (String cpf, LocalDateTime ini, LocalDateTime fim){
        return pedidoRepository.porPeriodo(cpf, ini, fim);
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
