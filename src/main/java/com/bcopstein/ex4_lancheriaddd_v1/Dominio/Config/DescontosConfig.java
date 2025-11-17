package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto20Dias;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto30Dias;

@Configuration // singleton
public class DescontosConfig {
    @Bean
    public CalculoDesconto30Dias clienteGastador(PedidoRepository repository) {
        return new CalculoDesconto30Dias(repository);
    }

    @Bean
    public CalculoDesconto20Dias clienteFrequente(PedidoRepository repository) {
        return new CalculoDesconto20Dias(repository);
    }
}
