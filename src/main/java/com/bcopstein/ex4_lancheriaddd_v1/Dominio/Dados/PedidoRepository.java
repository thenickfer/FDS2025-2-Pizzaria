package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface PedidoRepository {
    Pedido submetePedido(Pedido ped);

    Pedido.Status getStatus(long id);

    Boolean cancelarPedido(long id);

    List<Pedido> ultimos20Dias(String cpf);
}
