package com.bcopstein.ex4_lancheriaddd_v1;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ImpostosAux.CalculoImposto;

@Test
@ExtendWith(MockitoExtensions.class)
public class CalcularImpostoTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private CalculoImposto claculoImposto;

    void calculaImposto(){
        Pedido ped = new Pedido(999, null, null, null, null, 0, 0, 0, 0);
        when(pedidoRepository.getPedidoPorId(999))
    }
}