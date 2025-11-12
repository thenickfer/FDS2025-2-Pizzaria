package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ClienteService;

public class CadastroClienteUC {

    private ClienteService clienteService;

    public CadastroClienteUC(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public CadastroClienteResponse run(CadastraroClienteRequest request) {
        
        
        // FAZER Exception DPS

        Cliente cliente = Cliente.builder()
            .cpf(request.getCpf())
            .nome(request.getNome())
            .celular(request.getCelular())
            .endereco(request.getEndereco())
            .email(request.getEmail())
            .build(); // ARRUMAR DPS ESSE BUILDER

        cliente = clienteService.cadastraCliente(cliente);

        return CadastroClienteResponse.fromEntity(cliente);
    }
}