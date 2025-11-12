package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastroClienteResponse {
    private String nome;
    private String celular;
    private String endereco;
    private String cpf;

    public static cadastroClienteResponse fromEntity(com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente cliente) {
        return new cadastroClienteResponse(
            cliente.getNome(),
            cliente.getCelular(),
            cliente.getEndereco(),
            cliente.getCpf()
        );
    }
}