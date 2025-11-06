package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class CalculoDesconto30Dias implements CalculoDesconto{

    private static final double TAXA = 0.15;
    private static final int QNTD_PARA_DESCONTO = 500;
    private PedidoRepository pedidoRepository;

    public CalculoDesconto30Dias(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
  
    public double calcularDesconto(Pedido p) { 
        if (p == null){
            return 0;
        }

        List<Pedido> nroPedidos = pedidoRepository.ultimos30Dias(p.getCliente().getCpf());

        double totalGasto = nroPedidos.stream()
                                        .mapToDouble(Pedido::getValorCobrado)
                                        .sum();
        if (totalGasto > QNTD_PARA_DESCONTO) {
            double desc = p.getValor() * TAXA;
            p.setDesconto(desc);
            return desc;
        }

        p.setDesconto(0);
        return 0;
    }
    
}
