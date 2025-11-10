package com.bcopstein.ex4_lancheriaddd_v1;

import org.junit.jupiter.api.Test;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostosAux.CalculoImpostoPadrao;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcularImpostoTest {
    @Test
    void calculaImposto() {
        Pedido ped = new Pedido(999, null, null, null, null, 100.0, 0.0, 0, 0);
        CalculoImpostoPadrao ci = new CalculoImpostoPadrao(); // instância real

        double imposto = ci.calcularImposto(ped);

        assertEquals(10.0, imposto, 1e-6);

        assertEquals(10.0, ped.getImpostos(), 1e-6);
    }
}
