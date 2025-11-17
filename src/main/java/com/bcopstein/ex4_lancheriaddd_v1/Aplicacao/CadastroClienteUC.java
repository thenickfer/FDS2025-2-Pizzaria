package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.CadastroClienteRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CadastroClienteResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CadastroService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class CadastroClienteUC {

    private CadastroService cadService;
    private PasswordEncoder passwordEncoder;


    public CadastroClienteUC(CadastroService cadService, PasswordEncoder passwordEncoder) {
        this.cadService = cadService;
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

        cliente = cadService.cadastrarCliente(cliente, user);

        return CadastroClienteResponse.fromEntity(cliente);
    }
}