package com.bcopstein.ex4_lancheriaddd_v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto20Dias;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux.CalculoDesconto30Dias;

public class CalcularDescontoTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Cliente cliente = new Cliente("68010088020", "Nome Teste", "999999999", "Rua X, 123", "teste@example.com");
        when(pedidoRepository.totalUltimos30Dias(cliente.getCpf())).thenReturn(1000.0);

        Pedido pedidoMock = new Pedido(0L, cliente, null, new ArrayList<ItemPedido>(), Pedido.Status.APROVADO, 500, 0,
                0, 0);

        when(pedidoRepository.getPedidoPorId(0L)).thenReturn(pedidoMock);

        Pedido pedidoMock2 = new Pedido(1L, cliente, null, new ArrayList<ItemPedido>(), Pedido.Status.APROVADO, 1000, 0,
                0, 0);

        when(pedidoRepository.getPedidoPorId(1L)).thenReturn(pedidoMock2);

        Pedido pedidoMock3 = new Pedido(5L, cliente, null, new ArrayList<ItemPedido>(), Pedido.Status.APROVADO, 1000, 0,
                0, 0);

        when(pedidoRepository.getPedidoPorId(5L)).thenReturn(pedidoMock3);

        Pedido pedidoMock4 = new Pedido(6L, cliente, null, new ArrayList<ItemPedido>(), Pedido.Status.APROVADO, 1000, 0,
                0, 0);

        when(pedidoRepository.getPedidoPorId(6L)).thenReturn(pedidoMock4);

        when(pedidoRepository.ultimos20Dias(cliente.getCpf()))
                .thenReturn(new ArrayList<>(Arrays.asList(pedidoMock, pedidoMock2, pedidoMock3, pedidoMock4)));

        cliente = new Cliente("55666111000", "Nome Teste", "999999999", "Rua X, 123", "teste@example.com");
        pedidoMock = new Pedido(2L, cliente, null, new ArrayList<ItemPedido>(), Pedido.Status.APROVADO, 200, 0,
                0, 0);

        when(pedidoRepository.getPedidoPorId(2L)).thenReturn(pedidoMock);
        when(pedidoRepository.totalUltimos30Dias(cliente.getCpf())).thenReturn(300.0);
        when(pedidoRepository.ultimos20Dias(cliente.getCpf()))
                .thenReturn(new ArrayList<>(Arrays.asList(pedidoMock)));
    }

    @ParameterizedTest
    @CsvSource({
            "68010088020, 0, 75",
            "68010088020, 1, 150",
            "55666111000, 2, 0"
    })
    void descontoGastador(String cpf, long idPedido, double expected) {

        CalculoDesconto calc = new CalculoDesconto30Dias(pedidoRepository);
        DescontoService descontoService = new DescontoService(calc);

        Pedido p = pedidoRepository.getPedidoPorId(idPedido);

        double desconto = descontoService.calcularDesconto(p);
        assertEquals(expected, desconto, 1e-6);
        assertEquals(expected, p.getDesconto(), 1e-6);

    }

    @ParameterizedTest
    @CsvSource({
            "68010088020, 0, 35",
            "68010088020, 1, 70",
            "55666111000, 2, 0"
    })
    void descontoFrequente(String cpf, long idPedido, double expected) {

        CalculoDesconto calc = new CalculoDesconto20Dias(pedidoRepository);
        DescontoService descontoService = new DescontoService(calc);

        Pedido p = pedidoRepository.getPedidoPorId(idPedido);

        double desconto = descontoService.calcularDesconto(p);
        assertEquals(expected, desconto, 1e-6);
        assertEquals(expected, p.getDesconto(), 1e-6);

    }
}
