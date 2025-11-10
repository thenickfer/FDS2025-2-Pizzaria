package com.bcopstein.ex4_lancheriaddd_v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto20Dias;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto30Dias;

@ExtendWith(MockitoExtension.class)
public class DescontoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Pedido pedido;

    @Mock
    private Cliente cliente;

    @InjectMocks
    private CalculoDesconto20Dias calc20;

    @Test
    void desconto30Dias() {
        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getCpf()).thenReturn("cpf-123");
        when(pedido.getValor()).thenReturn(200.0);
        when(pedidoRepository.totalUltimos30Dias("cpf-123")).thenReturn(501.0); // > 500

        CalculoDesconto30Dias calc30 = new CalculoDesconto30Dias(pedidoRepository);
        double desconto = calc30.calcularDesconto(pedido);

        assertEquals(200.0 * 0.15, desconto, 1e-6);
        verify(pedido).setDesconto(200.0 * 0.15);
    }

    @Test
    void desconto20Dias() {
        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getCpf()).thenReturn("cpf-123");
        when(pedido.getValor()).thenReturn(150.0);

        when(pedidoRepository.ultimos20Dias("cpf-123"))
            .thenReturn(Arrays.asList(
                org.mockito.Mockito.mock(Pedido.class),
                org.mockito.Mockito.mock(Pedido.class),
                org.mockito.Mockito.mock(Pedido.class),
                org.mockito.Mockito.mock(Pedido.class)
            ));

        double desconto = calc20.calcularDesconto(pedido);

        assertEquals(150.0 * 0.07, desconto, 1e-6);
        verify(pedido).setDesconto(150.0 * 0.07);
    }
}