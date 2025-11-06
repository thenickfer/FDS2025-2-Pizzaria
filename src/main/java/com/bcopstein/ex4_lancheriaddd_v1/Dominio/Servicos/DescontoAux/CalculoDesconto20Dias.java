package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class CalculoDesconto20Dias implements CalculoDesconto{
    private static final int QNTD_PARA_DESCONTO = 3;
    private static final double TAXA = 0.07;
    private PedidoRepository pedidoRepository;

    public CalculoDesconto20Dias(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public double calcularDesconto(Pedido p){
        if (p == null) {
            return 0;
        }

        List<Pedido> nroPedidos = pedidoRepository.ultimos20Dias(p.getCliente().getCpf()); 

        if (nroPedidos.size() > QNTD_PARA_DESCONTO) {
            double desc = p.getValor() * TAXA;
            p.setDesconto(desc);
            return desc;
        }

        p.setDesconto(0);

        return 0;
    }


}

