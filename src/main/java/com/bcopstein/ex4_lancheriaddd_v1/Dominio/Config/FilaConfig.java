package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Configuration // singleton
public class FilaConfig {
    @Bean
    public BlockingQueue<Pedido> filaSaida() {
        return new LinkedBlockingQueue<>();
    }
}
