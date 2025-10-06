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
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
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

        List<Object[]> batchArgs = new ArrayList<>();

        for (ItemPedido item : ped.getItens()) {
            batchArgs.add(new Object[] { genId, item.getId() }); //ARRUMAR
        }

        this.jdbcTemplate.batchUpdate("INSERT INTO pedido_itemPedido (id_pedido, id_itemPedido) VALUES (?, ?)",
                batchArgs);

        return ped;
    }// Add pedido_Cliente e pedido_Itempedido

    @Override
    public Pedido.Status getStatus(long id) {
        String sql = "SELECT p.estado " +
                "FROM pedidos p " +
                "WHERE p.id = ? ";
        List<String> statusList = this.jdbcTemplate.query(
                sql,
                ps -> ps.setLong(1, id),
                (rs, rowNum) -> {
                    return rs.getString("estado");
                });
        String status = statusList.getFirst();
        return Pedido.Status.valueOf(status);
    }

    public Pedido.Status selection(String status){
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
            case "NEGADO":
                return Pedido.Status.NEGADO;
            default:
                return null;
        }
    }

    @Override
    public Boolean cancelarPedido(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelarPedido'");
    }

    public List<Pedido> ultimos20Dias (String cpf){
        String sql = "SELECT p.id as pedidoId, p.estado, p.data_hora_pagamento, p.valor, p.imposto, p.desconto, p.valor_cobrado, c.cpf, c.nome, c.celular, c.endereco, c.email " +
                     "FROM cliente c " +
                     "JOIN pedido_cliente pc on c.cliente_cpf = pc.cliente_cpf " +
                     "JOIN pedido p on pc.pedido_id = p.id " +
                     "WHERE c.cpf = ? and p.data_hora_pagamento >= DATEADD('DAY',-20,CURRENT_TIMESTAMP)";

        //possivel consulta completa
        String sql2 = "SELECT p.id as pedidoId, p.estado, p.data_hora_pagamento, p.valor, " +
                     "p.imposto, p.desconto, p.valor_cobrado, c.cpf, c.nome, c.celular, " + 
                     "c.endereco, c.email, iped.id_itemPedido as itemPedidoID, iped.quantidade as itemPedidoQuant " +
                     "FROM cliente c " +
                     "JOIN pedido_cliente pc on c.cliente_cpf = pc.cliente_cpf " +
                     "JOIN pedido p on pc.pedido_id = p.id " +
                     "LEFT JOIN pedido_itemPedido pedItem on p.id = pedItem.id_pedido " +
                     "LEFT JOIN itemPedido iPed on pedItem.id_itemPedido = iped.id_itemPedido " +
                     "LEFT JOIN itemPedido_produto iPedProd on iped.id_itemPedido = iPedProd.id_itemPedido " +
                     "LEFT JOIN produtos prod on iPedProd.id_produto = prod.id " +

                     "WHERE c.cpf = ? and p.data_hora_pagamento >= DATEADD('DAY',-20,CURRENT_TIMESTAMP)";
        
        List<Pedido> pedidos = this.jdbcTemplate.query(
            sql, 
            ps -> ps.setString(1,cpf),
            (rs,rowNum) ->{
                long id = rs.getLong("pedidoId");

                //variaveis cliente
                String cpfAux = rs.getString("cpf");
                String nome = rs.getString("nome");
                String celular = rs.getString("celular");
                String endereco = rs.getString("endereco");
                String email = rs.getString("email");

                Cliente cli = new Cliente (cpfAux,nome,celular,endereco,email);
                Timestamp dateAux = rs.getTimestamp("data_hora_pagamento");
                LocalDateTime date = dateAux.toLocalDateTime();

                //daqui pra baixo: sera??
                //variaveis itempedido
                long idItemPedido = rs.getLong("itemPedidoID");
                //Produto item
                int quantidade = rs.Int("itemPedidoQuant");
                ItemPedido itemPedi = new ItemPedido(idItemPedido, null, rowNum)

                //daqui pra baixo: bom
                List<ItemPedido> itens = null; //ARRUMAR
                Pedido.Status status = Pedido.Status.valueOf(rs.getString("estado"));
                double valor = rs.getDouble("valor");
                double impostos = rs.getDouble("imposto");
                double desconto = rs.getDouble("desconto");
                double valorCobrado = rs.getDouble("valor_cobrado");
                
                Pedido p = new Pedido(id, cli,date,itens,status,valor,impostos,desconto,valorCobrado);
                return p;
            });
        return pedidos;
    }

}
