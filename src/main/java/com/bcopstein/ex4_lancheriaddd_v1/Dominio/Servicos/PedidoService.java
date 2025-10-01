package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class PedidoService {
    private PedidoRepository pedidoRepository;
    private CozinhaService cozinhaService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, CozinhaService cozinhaService) {
        this.pedidoRepository = pedidoRepository;
        this.cozinhaService = cozinhaService;
    }

    public Pedido submetePedido(Pedido ped) {
        ped = pedidoRepository.submetePedido(ped);
        if(ped.getStatus() == Pedido.Status.APROVADO){
            cozinhaService.chegadaDePedido(ped);
        }
        return ped;
    }

    public Pedido.Status geStatus(long id) {
        return pedidoRepository.getStatus(id);
    }

    public Boolean cancelarPedido(long id) {
        return pedidoRepository.cancelarPedido(id);
    }
}
