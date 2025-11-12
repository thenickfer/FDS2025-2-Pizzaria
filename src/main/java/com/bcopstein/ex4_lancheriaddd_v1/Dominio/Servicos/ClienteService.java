package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ClientesRepository;

@Service
public class ClienteService {
    private final ClientesRepository clientesRepository;

    @Autowired
    public ClienteService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public Cliente getByCpf(String cpf) {
        Cliente cliente = clientesRepository.getByCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado com Cpf: " + cpf);
        }
        return cliente;
    }

    public Cliente cadastraCliente(Cliente cliente) {
        if (clientesRepository.getByCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("Cliente já cadastrado com Cpf: " + cliente.getCpf());
        }
        return clientesRepository.cadastraCliente(cliente);
    }

}
