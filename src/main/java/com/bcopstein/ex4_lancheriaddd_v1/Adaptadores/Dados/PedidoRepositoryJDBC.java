package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido.Status;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;

@Component
public class PedidoRepositoryJDBC implements PedidoRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PedidoRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Pedido submetePedido(Pedido ped) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO pedidos (estado, data_hora_pagamento, valor, imposto, desconto, valor_cobrado) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ped.getStatus().toString());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(ped.getDataHoraPagamento()));
            ps.setDouble(3, ped.getValor());
            ps.setDouble(4, ped.getImpostos());
            ps.setDouble(5, ped.getDesconto());
            ps.setDouble(6, ped.getValorCobrado());
            return ps;
        }, keyHolder);

        long genId = keyHolder.getKey().longValue();
        ped.setId(genId);

        this.jdbcTemplate.update("INSERT INTO pedido_cliente (pedido_id, cliente_cpf) VALUES (?, ?)",
                genId,
                ped.getCliente().getCpf());

        // usar batch update ao inves de string, string tem risco de sql injection
        // POR ALGUM MOTIVO, ITEMPEDIDO NAO TEM ID????
        /*
         * List<Object[]> batchArgs = new ArrayList<>();
         * 
         * for (ItemPedido item : ped.getItens()) {
         * batchArgs.add(new Object[]{genId, item.getId()});
         * }
         * 
         * this.jdbcTemplate.
         * batchUpdate("INSERT INTO pedido_itemPedido (id_pedido, id_itemPedido) VALUES (?, ?)"
         * ,
         * batchArgs);
         */

        return ped;
    }// Add pedido_Cliente e pedido_Itempedido

    @Override
    public Pedido.Status getStatus(long id) {
        String sql = "SELECT p.estado" +
                "FROM pedidos p" +
                "WHERE p.id = ?";
        List<String> statusList = this.jdbcTemplate.query(
                sql,
                ps -> ps.setLong(1, id),
                (rs, rowNum) -> {
                    return rs.getString("estado");
                });
        String status = statusList.getFirst();
        switch (status) {
            case "NOVO":
                return Pedido.Status.NOVO;
            case "APROVADO":
                return Pedido.Status.APROVADO;
            case "PAGO":
                return Pedido.Status.PAGO;
            case "AGUARDANDO":
                return Pedido.Status.AGUARDANDO;
            case "PREPARACAO":
                return Pedido.Status.PREPARACAO;
            case "PRONTO":
                return Pedido.Status.PRONTO;
            case "TRANSPORTE":
                return Pedido.Status.TRANSPORTE;
            case "ENTREGUE":
                return Pedido.Status.ENTREGUE;
            default:
                return null;
        }
    }

    @Override
    public Boolean cancelarPedido(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarPedido'");
    }

}
