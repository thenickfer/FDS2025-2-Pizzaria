package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.DescontoAux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class CalculoDesconto30Dias implements CalculoDesconto {

    private static final double TAXA = 0.15;
    private static final int QNTD_PARA_DESCONTO = 500;
    private PedidoRepository pedidoRepository;

    @Autowired
    public CalculoDesconto30Dias(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public double calcularDesconto(Pedido p) {
        if (p == null) {
            return 0;
        }

        double totalGasto = pedidoRepository.totalUltimos30Dias(p.getCliente().getCpf());

        if (totalGasto > QNTD_PARA_DESCONTO) {
            double desc = p.getValor() * TAXA;
            p.setDesconto(desc);
            return desc;
        }

        p.setDesconto(0);
        return 0;
    }

}
