package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import java.time.LocalDateTime;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public interface PedidoRepository {
    Pedido submetePedido(Pedido ped);

    Pedido.Status getStatus(long id);

    boolean cancelarPedido(long id); // LEMBRAR DE DELETAR ITENS PEDIDO e ITENSPEDIDO_PEDIDO DO DB

    List<Pedido> ultimos20Dias(String cpf);

    boolean pagarPedido(long id);

    boolean atualizarStatus(long id, Pedido.Status status);

    List<Pedido> porPeriodo(String cpf, LocalDateTime ini, LocalDateTime fim);

    Pedido getPedidoPorId(long id);
}
