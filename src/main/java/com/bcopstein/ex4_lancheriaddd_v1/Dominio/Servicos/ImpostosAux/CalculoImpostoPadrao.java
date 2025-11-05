package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostosAux;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class CalculoImpostoPadrao {

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
