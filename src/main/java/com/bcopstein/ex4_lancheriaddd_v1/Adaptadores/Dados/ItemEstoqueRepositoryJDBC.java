package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;

public class ItemEstoqueRepositoryJDBC implements ItemEstoqueRepository {
    private JdbcTemplate jdbcTemplate;

    public ItemEstoqueRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ItemEstoque> getAll() {
        List<ItemEstoque> resp = jdbcTemplate.query(
            "SELECT id, quantidade, ingrediente_id FROM itensEstoque",
            (rs, rowNum) -> {
                Ingrediente ing = new Ingrediente(rs.getLong("ingrediente_id"));
                return new ItemEstoque(ing, rs.getInt("quantidade"));
            }
        );
        return resp;
    }
}
