package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.ClienteRegisteredException;

import jakarta.transaction.Transactional;

@Service
public class CadastroService {
    private final JdbcUserDetailsManager userManager;
    private ClientesRepository repo;

    @Autowired
    public CadastroService(JdbcUserDetailsManager userManager, JdbcTemplate jdbc, PasswordEncoder encoder, ClientesRepository repo) {
        this.userManager = userManager;
        this.repo = repo;
    }

  @Transactional
  public boolean cadastrarCliente(Cliente cli, UserDetails user) {

    if(userManager.userExists(user.getUsername()) || !(repo.getByCpf(cli.getCpf())==null)){
        throw new ClienteRegisteredException(user.getUsername(),cli.getCpf());
    }
    userManager.createUser(user);

    //BOTAR IF
    if (!repo.cadastro(cli.getCpf(), cli.getNome(), cli.getCelular(), cli.getEndereco(), cli.getEmail())){
        throw new IllegalStateException("Problema ao registrar cliente");
    }
    return true;
  }
}
