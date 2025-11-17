package com.bcopstein.ex4_lancheriaddd_v1;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.CadastroClienteRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ClienteService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class CadastroClienteUC {

    private ClienteService clienteService;
    private passwordEncoder passwordEncoder;


    public CadastroClienteUC(ClienteService clienteService, PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }

    public CadastroClienteResponse run(CadastroClienteRequest request) {
        if(request.getSenha() == null){
            throw new IllegalArgumentException("senha deve existir");
        }
        if(request.getEmail() == null){
            throw new IllegalArgumentException("email deve existir");
        }

        Cliente cliente = Cliente.builder()
            .cpf(request.getCpf())
            .nome(request.getNome())
            .celular(request.getCelular())
            .endereco(request.getEndereco())
            .email(request.getEmail())
            .build();

        UserDetails user = User.withUsername(request.getEmail())
            .password(passwordEncoder.encode(request.getSenha()))
            .roles("CLIENTE")
            .build();

        cliente = clienteService.cadastraCliente(cliente, user);

        return CadastroClienteResponse.fromEntity(cliente);
    }
}