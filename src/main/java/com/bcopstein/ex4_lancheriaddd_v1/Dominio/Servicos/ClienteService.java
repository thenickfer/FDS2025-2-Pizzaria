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

    public Cliente getByID(String cpf) {
        Cliente cliente = clientesRepository.getById(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado com Cpf: " + cpf);
        }
        return cliente;
    }

}
