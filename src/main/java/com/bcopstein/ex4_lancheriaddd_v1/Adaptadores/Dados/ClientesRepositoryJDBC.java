package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class ClientesRepositoryJDBC implements ClientesRepository {
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;

    public ClientesRepositoryJDBC(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
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

    public Cliente cadastraCliente(Cliente cliente) {
        String sql = "INSERT INTO CLIENTES (cpf, nome, celular, endereco, email) VALUES (?, ?, ?, ?, ?)";

        int rows = this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getCelular());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getEmail());
            return ps;
        });

        if (rows == 1) {
            return cliente;
        } else {
            throw new RuntimeException("Falha ao salvar cliente, registro não inserido.");
        }
    }

}