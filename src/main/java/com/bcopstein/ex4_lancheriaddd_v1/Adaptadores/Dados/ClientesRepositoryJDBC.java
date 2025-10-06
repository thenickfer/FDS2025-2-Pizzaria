package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

public class ClientesRepositoryJDBC implements ClientesRepository {
    private JdbcTemplate jdbcTemplate;

    public ClientesRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cliente getByCpf(String cpf) {
        String sql = "SELECT c.cpf, c.nome, c.celular, c.endereco, c.email " +
                "FROM CLIENTES c " +
                "WHERE c.cpf = ?";

        List<Cliente> clientes = this.jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, cpf),
                (rs, rowNum) -> {
                    String cpfCli = rs.getString("cpf");
                    String nomeCli = rs.getString("nome");
                    String celularCli = rs.getString("celular");
                    String enderecoCli = rs.getString("endereco");
                    String emailCli = rs.getString("email");
                    return new Cliente(cpfCli, nomeCli, celularCli, enderecoCli, emailCli);
                });
        return clientes.isEmpty() ? null : clientes.get(0);

    }
}