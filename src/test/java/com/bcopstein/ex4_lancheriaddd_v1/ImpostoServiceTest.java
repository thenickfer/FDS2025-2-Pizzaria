package com.bcopstein.ex4_lancheriaddd_v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostosAux.CalculoImposto;

public class ImpostoServiceTest {
    private CalculoImposto calculoImposto;
    private ImpostoService impostoService;

    @BeforeEach
    void setUp() {
        this.calculoImposto = mock(CalculoImposto.class);
        this.impostoService = new ImpostoService(calculoImposto);
    }

    @Test
    void calculoImpostoNoService() {
        Pedido pedido = new Pedido(999, null, null, null, null, 150.0, 0.0, 0, 0);
        when(calculoImposto.calcularImposto(pedido)).thenReturn(22.5);

        double resultado = impostoService.calcularImposto(pedido);

        assertEquals(22.5, resultado, 1e-6);
        verify(calculoImposto).calcularImposto(pedido);
        verifyNoMoreInteractions(calculoImposto);
    }
}
