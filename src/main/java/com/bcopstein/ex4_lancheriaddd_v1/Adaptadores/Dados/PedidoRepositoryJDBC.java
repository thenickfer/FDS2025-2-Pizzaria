package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
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

        for (int i = 0; i < ped.getItens().size(); i++) {
            ItemPedido item = ped.getItens().get(i);
            KeyHolder itemKeyHolder = new GeneratedKeyHolder();
            this.jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO itemPedido (quantidade) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, item.getQuantidade());
                return ps;
            }, itemKeyHolder);
            long itemPedidoId = itemKeyHolder.getKey().longValue();
            ped.getItens().set(i, new ItemPedido(itemPedidoId, item.getItem(), item.getQuantidade()));
            batchArgs.add(new Object[] { genId, itemPedidoId }); // ARRUMAR
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

    @Override
    public Boolean cancelarPedido(long id) { 
        String sql = "UPDATE pedidos SET estado = 'CANCELADO' WHERE id = ?";
        int access = this.jdbcTemplate.update(sql,id);
        if (access > 0){return true;}
        else {return false;}
    }

    @Override
    public List<Pedido> ultimos20Dias(String cpf) {
        String sql = "SELECT p.id as pedidoId, p.estado, p.data_hora_pagamento, p.valor, " +
                "p.imposto, p.desconto, p.valor_cobrado, c.cpf, c.nome, c.celular, " +
                "c.endereco, c.email, iped.id_itemPedido as itemPedidoID, iped.quantidade as itemPedidoQuant, " +
                "prod.id as produtoID, prod.descricao as produtoDesc, prod.preco as produtoPreco, " +
                "rc.id as receitaID, rc.titulo as receitaTitulo, " +
                "ing.id as ingreID, ing.descricao as ingreDesc " +
                "FROM clientes c " +
                "JOIN pedido_cliente pc on c.cliente_cpf = pc.cliente_cpf " +
                "JOIN pedidos p on pc.pedido_id = p.id " +
                "LEFT JOIN pedido_itemPedido pedItem on p.id = pedItem.id_pedido " +
                "LEFT JOIN itemPedido iPed on pedItem.id_itemPedido = iped.id_itemPedido " +
                "LEFT JOIN itemPedido_produto iPedProd on iped.id_itemPedido = iPedProd.id_itemPedido " +
                "LEFT JOIN produtos prod on iPedProd.id_produto = prod.id " +
                "LEFT JOIN produto_receita prodReceita on prod.id = prodReceita.produto_id " +
                "LEFT JOIN receitas rc on prodReceita.receita_id = rc.id " +
                "LEFT JOIN receita_ingrediente ri on rc.id = ri.receita_id " +
                "LEFT JOIN ingredientes ing on ri.ingrediente_id = ing.id " +
                "WHERE c.cpf = ? and p.data_hora_pagamento >= DATEADD('DAY',-20,CURRENT_TIMESTAMP)";

        List<Pedido> pedidos = this.jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, cpf),
                rs -> {
                    Map<Long, Pedido> pedidosMap = new LinkedHashMap<>(); // para nao ter pedidos repetidos
                    Map<Long, Set<Long>> ingredientesPorItem = new HashMap<>(); // para nao ter o mesmo ingrediente
                                                                                // varias vezes

                    while (rs.next()) {
                        long id = rs.getLong("pedidoId");
                        Pedido p = pedidosMap.get(id);
                        if (p == null) {
                            // variaveis cliente
                            String cpfAux = rs.getString("cpf");
                            String nome = rs.getString("nome");
                            String celular = rs.getString("celular");
                            String endereco = rs.getString("endereco");
                            String email = rs.getString("email");
                            Cliente cli = new Cliente(cpfAux, nome, celular, endereco, email);

                            // data
                            Timestamp dateAux = rs.getTimestamp("data_hora_pagamento");
                            LocalDateTime date = dateAux != null ? dateAux.toLocalDateTime() : null;

                            // status
                            String estado = rs.getString("estado");
                            Pedido.Status status = estado != null ? Pedido.Status.valueOf(estado) : null;

                            // valores
                            double valor = rs.getDouble("valor");
                            double impostos = rs.getDouble("imposto");
                            double desconto = rs.getDouble("desconto");
                            double valorCobrado = rs.getDouble("valor_cobrado");

                            p = new Pedido(id, cli, date, new ArrayList<>(), status, valor, impostos, desconto,
                                    valorCobrado);
                            pedidosMap.put(id, p);
                        }

                        // item do pedido
                        Long idItemPedido = rs.getObject("itemPedidoID", Long.class);
                        if (idItemPedido == null) {
                            continue;
                        }

                        ItemPedido itemPedi = null;
                        for (ItemPedido it : p.getItens()) {
                            if (it.getId() == idItemPedido) {
                                itemPedi = it;
                                break;
                            }
                        }

                        if (itemPedi == null) {
                            int quantidadeItemPedido = rs.getInt("itemPedidoQuant");

                            // produto
                            Long idProduto = rs.getObject("produtoID", Long.class);
                            Produto produto = null;
                            if (idProduto != null) {
                                String descProduto = rs.getString("produtoDesc");
                                int precoProduto = rs.getInt("produtoPreco"); // alterar???

                                // receita
                                Long idReceita = rs.getObject("receitaID", Long.class);
                                Receita receita = null;
                                if (idReceita != null) {
                                    String tituloReceita = rs.getString("receitaTitulo");
                                    receita = new Receita(idReceita, tituloReceita, new ArrayList<>());
                                }
                                produto = new Produto(idProduto, descProduto, receita, precoProduto);
                            }

                            itemPedi = new ItemPedido(idItemPedido, produto, quantidadeItemPedido);
                            p.getItens().add(itemPedi);
                        }

                        // ingredientes
                        Long idReceita2 = rs.getObject("receitaID", Long.class);
                        Long idIngre = rs.getObject("ingreID", Long.class);
                        if (idReceita2 != null && idIngre != null && itemPedi.getItem() != null
                                && itemPedi.getItem().getReceita() != null) {
                            Set<Long> set = ingredientesPorItem.computeIfAbsent(idItemPedido, k -> new HashSet<>());
                            if (set.add(idIngre)) {
                                String descIngre = rs.getString("ingreDesc");
                                Ingrediente ingrediente = new Ingrediente(idIngre, descIngre);
                                List<Ingrediente> listaIng = itemPedi.getItem().getReceita().getIngredientes();
                                if (listaIng != null) {
                                    listaIng.add(ingrediente);
                                }
                            }
                        }
                    }
                    return new ArrayList<>(pedidosMap.values());
                });
        return pedidos;
    }

    @Override
    public Boolean pagarPedido(long id) {
        String sql = "UPDATE pedidos SET estado = 'PAGO' WHERE id = ?";
        int access = this.jdbcTemplate.update(sql,id);
        if (access > 0){return true;}
        else {return false;}
    }

    @Override
    public Boolean atualizarStatus(long id, Pedido.Status status) {
        String statusString = status.toString();
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        int access = this.jdbcTemplate.update(sql, statusString, id);
        if (access > 0){return true;}
        else {return false;}
    }

    @Override
    public List<Pedido> porPeriodo(String cpf, LocalDateTime ini, LocalDateTime fim) {
        String sql = "SELECT p.id as pedidoId, p.estado, p.data_hora_pagamento, p.valor, " +
                "p.imposto, p.desconto, p.valor_cobrado, c.cpf, c.nome, c.celular, " +
                "c.endereco, c.email, iped.id_itemPedido as itemPedidoID, iped.quantidade as itemPedidoQuant, " +
                "prod.id as produtoID, prod.descricao as produtoDesc, prod.preco as produtoPreco, " +
                "rc.id as receitaID, rc.titulo as receitaTitulo, " +
                "ing.id as ingreID, ing.descricao as ingreDesc " +
                "FROM clientes c " +
                "JOIN pedido_cliente pc on c.cliente_cpf = pc.cliente_cpf " +
                "JOIN pedidos p on pc.pedido_id = p.id " +
                "LEFT JOIN pedido_itemPedido pedItem on p.id = pedItem.id_pedido " +
                "LEFT JOIN itemPedido iPed on pedItem.id_itemPedido = iped.id_itemPedido " +
                "LEFT JOIN itemPedido_produto iPedProd on iped.id_itemPedido = iPedProd.id_itemPedido " +
                "LEFT JOIN produtos prod on iPedProd.id_produto = prod.id " +
                "LEFT JOIN produto_receita prodReceita on prod.id = prodReceita.produto_id " +
                "LEFT JOIN receitas rc on prodReceita.receita_id = rc.id " +
                "LEFT JOIN receita_ingrediente ri on rc.id = ri.receita_id " +
                "LEFT JOIN ingredientes ing on ri.ingrediente_id = ing.id " +
                "WHERE c.cpf = ? and p.data_hora_pagamento BETWEEN ? and ?";

        List<Pedido> pedidos = this.jdbcTemplate.query(
                sql,
                ps -> {
                    ps.setString(1, cpf);
                    ps.setTimestamp(2, Timestamp.valueOf(ini));
                    ps.setTimestamp(3, Timestamp.valueOf(fim));
                },
                rs -> {
                    Map<Long, Pedido> pedidosMap = new LinkedHashMap<>(); // para nao ter pedidos repetidos
                    Map<Long, Set<Long>> ingredientesPorItem = new HashMap<>(); // para nao ter o mesmo ingrediente
                                                                                // varias vezes

                    while (rs.next()) {
                        long id = rs.getLong("pedidoId");
                        Pedido p = pedidosMap.get(id);
                        if (p == null) {
                            // variaveis cliente
                            String cpfAux = rs.getString("cpf");
                            String nome = rs.getString("nome");
                            String celular = rs.getString("celular");
                            String endereco = rs.getString("endereco");
                            String email = rs.getString("email");
                            Cliente cli = new Cliente(cpfAux, nome, celular, endereco, email);

                            // data
                            Timestamp dateAux = rs.getTimestamp("data_hora_pagamento");
                            LocalDateTime date = dateAux != null ? dateAux.toLocalDateTime() : null;

                            // status
                            String estado = rs.getString("estado");
                            Pedido.Status status = estado != null ? Pedido.Status.valueOf(estado) : null;

                            // valores
                            double valor = rs.getDouble("valor");
                            double impostos = rs.getDouble("imposto");
                            double desconto = rs.getDouble("desconto");
                            double valorCobrado = rs.getDouble("valor_cobrado");

                            p = new Pedido(id, cli, date, new ArrayList<>(), status, valor, impostos, desconto,
                                    valorCobrado);
                            pedidosMap.put(id, p);
                        }

                        // item do pedido
                        Long idItemPedido = rs.getObject("itemPedidoID", Long.class);
                        if (idItemPedido == null) {
                            continue;
                        }

                        ItemPedido itemPedi = null;
                        for (ItemPedido it : p.getItens()) {
                            if (it.getId() == idItemPedido) {
                                itemPedi = it;
                                break;
                            }
                        }

                        if (itemPedi == null) {
                            int quantidadeItemPedido = rs.getInt("itemPedidoQuant");

                            // produto
                            Long idProduto = rs.getObject("produtoID", Long.class);
                            Produto produto = null;
                            if (idProduto != null) {
                                String descProduto = rs.getString("produtoDesc");
                                int precoProduto = rs.getInt("produtoPreco"); // alterar???

                                // receita
                                Long idReceita = rs.getObject("receitaID", Long.class);
                                Receita receita = null;
                                if (idReceita != null) {
                                    String tituloReceita = rs.getString("receitaTitulo");
                                    receita = new Receita(idReceita, tituloReceita, new ArrayList<>());
                                }
                                produto = new Produto(idProduto, descProduto, receita, precoProduto);
                            }

                            itemPedi = new ItemPedido(idItemPedido, produto, quantidadeItemPedido);
                            p.getItens().add(itemPedi);
                        }

                        // ingredientes
                        Long idReceita2 = rs.getObject("receitaID", Long.class);
                        Long idIngre = rs.getObject("ingreID", Long.class);
                        if (idReceita2 != null && idIngre != null && itemPedi.getItem() != null
                                && itemPedi.getItem().getReceita() != null) {
                            Set<Long> set = ingredientesPorItem.computeIfAbsent(idItemPedido, k -> new HashSet<>());
                            if (set.add(idIngre)) {
                                String descIngre = rs.getString("ingreDesc");
                                Ingrediente ingrediente = new Ingrediente(idIngre, descIngre);
                                List<Ingrediente> listaIng = itemPedi.getItem().getReceita().getIngredientes();
                                if (listaIng != null) {
                                    listaIng.add(ingrediente);
                                }
                            }
                        }
                    }
                    return new ArrayList<>(pedidosMap.values());
                });
        return pedidos;
    }

    public Pedido getPedidoPorId(long id){
        String sql = "SELECT p.id as pedidoId, p.estado, p.data_hora_pagamento, p.valor, p.imposto, " +
            "p.desconto, p.valor_cobrado, ip.id_itemPedido as itemPedidoID, ip.quantidade as itemPedidoQuant, " +
            "c.cpf, c.nome, c.celular, c.endereco, c.email " +
            "FROM pedidos p " +
            "JOIN pedido_cliente pc on pc.pedido_id = p.id " +
            "JOIN clientes c on c.cpf = pc.cliente_cpf " +
            "LEFT JOIN pedido_itemPedido pi on pi.id_pedido = p.id " +
            "LEFT JOIN itemPedido ip on ip.id_itemPedido = pi.id_itemPedido " +
            "WHERE p.id = ?";

        Pedido pedido = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            rs -> {
                Pedido resultPedido = null;
                List<ItemPedido> itens = new ArrayList<>();
                while (rs.next()) {
                    if (resultPedido == null) {
                        long pedidoId = rs.getLong("pedidoId");
                        String pedidoEstado = rs.getString("estado");
                        Timestamp pedidoDataHoraPagemento = rs.getTimestamp("data_hora_pagamento");
                        double pedidoValor = rs.getDouble("valor");
                        double pedidoImposto = rs.getDouble("imposto");
                        double pedidoDesconto = rs.getDouble("desconto");
                        double pedidoValorCobrado = rs.getDouble("valor_cobrado");

                        Cliente cliente = new Cliente(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("celular"),
                            rs.getString("endereco"),
                            rs.getString("email")
                        );

                        resultPedido = new Pedido(
                            pedidoId,
                            cliente,
                            pedidoDataHoraPagemento != null ? pedidoDataHoraPagemento.toLocalDateTime() : null,
                            itens,
                            Pedido.Status.valueOf(pedidoEstado),
                            pedidoValor,
                            pedidoImposto,
                            pedidoDesconto,
                            pedidoValorCobrado
                        );
                    }
                    Long idItemPedido = (Long) rs.getObject("itemPedidoID"); 
                    Integer quantidade = (Integer) rs.getObject("itemPedidoQuant"); 
                    if(idItemPedido != null){
                        ItemPedido itemPedido = new ItemPedido(idItemPedido, null, quantidade);
                        itens.add(itemPedido);
                    }
                }
                return resultPedido;
            }
        );
        return pedido;
    }

}
