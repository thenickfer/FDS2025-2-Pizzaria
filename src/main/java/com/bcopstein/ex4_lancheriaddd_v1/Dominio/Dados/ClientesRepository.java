package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import org.springframework.security.core.userdetails.UserDetails;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

public interface ClientesRepository {
  Cliente getByCpf(String cpf);
  boolean cadastro(String cpf, String nome, String celular, String endereco, String email);
}
