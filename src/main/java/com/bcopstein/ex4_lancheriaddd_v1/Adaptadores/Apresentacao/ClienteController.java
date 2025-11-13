package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.CadastroClienteRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CadastroClienteResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.CadastroClienteUC;
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private CadastroClienteUC cadastroClienteUC;

    public ClienteController(CadastroClienteUC cadastroClienteUC) {
        this.cadastroClienteUC = cadastroClienteUC;
    }  

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroClienteResponse> cadastraCliente(@RequestBody CadastroClienteRequest request) {
        CadastroClienteResponse response = cadastroClienteUC.run(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    
    }
}