package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CozinhaService;

public class SubmetePedidoUC {
    private CozinhaService cozinhaService;

    @Autowired
    public SubmetePedidoUC(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }
}
