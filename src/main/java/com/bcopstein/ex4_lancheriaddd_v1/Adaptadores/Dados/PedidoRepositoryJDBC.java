package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido.Status;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Receita;

@Component
public class PedidoRepositoryJDBC implements PedidoRepository{
    private JdbcTemplate jdbcTemplate;
    private PedidoRepository pedidoRepository;

    @Autowired
    public PedidoRepositoryJDBC (JdbcTemplate jdbcTemplate, PedidoRepository pedidoRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.pedidoRepository = pedidoRepository;

    }


    @Override
    public Pedido submetePedido(Pedido ped) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'submetePedido'");
    }

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
            }
        );
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
