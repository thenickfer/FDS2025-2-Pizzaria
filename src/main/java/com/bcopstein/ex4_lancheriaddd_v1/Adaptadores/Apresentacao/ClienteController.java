package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private cadastroClienteUC cadastroClienteUC;

    public ClienteController(cadastroClienteUC cadastroClienteUC) {
        this.cadastroClienteUC = cadastroClienteUC;
    }  

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroClienteResponse> cadastraCliente(@RequestBody CadastroClienteRequest request) {
        CadastroClienteResponse response = cadastroClienteUC.run(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    
    }
}