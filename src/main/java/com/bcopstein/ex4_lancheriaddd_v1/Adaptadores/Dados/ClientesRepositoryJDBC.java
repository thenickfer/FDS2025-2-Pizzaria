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

    public boolean cadastro(String cpf, String nome, String celular, String endereco, String email){
        String sql = "INSERT into clientes (cpf, nome, celular, endereco, email) values (?, ?, ?, ?, ?)";
        int var = 0;

        var = jdbcTemplate.update(sql,
         ps -> {
            ps.setString(1, cpf);
            ps.setString(2, nome);
            ps.setString(3, celular);
            ps.setString(4, endereco);
            ps.setString(5, email);
         });
            if (var != 1) {
                throw new IllegalStateException("Esperava inserir 1 linha, inseriu: " + var);
            }

        sql = "update clientes set username = ? where cpf = ?";

        var = jdbcTemplate.update(sql,
        ps-> {
            ps.setString(1, email);
            ps.setString(2, cpf);
        });
            if (var != 1) {
                throw new IllegalStateException("Esperava inserir 1 linha, inseriu: " + var);
            }


        return true;
    }
}