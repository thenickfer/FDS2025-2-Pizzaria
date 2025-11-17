package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import org.springframework.security.core.userdetails.UserDetails;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

public interface ClientesRepository {
  Cliente getByCpf(String cpf);

  Cliente cadastraCliente(Cliente cliente, UserDetails us);
}
