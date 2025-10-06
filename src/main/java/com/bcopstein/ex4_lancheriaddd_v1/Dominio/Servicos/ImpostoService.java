package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class ImpostoService {

    private static final double TAXA = 0.10;

    public double calcularImposto(Pedido pedido) {
        double val = pedido.getValor() - pedido.getDesconto();

        if (val <= 0) {
            return 0;
        } else {
            pedido.setImpostos(val * TAXA);
            return val * TAXA;
        }
    }
}