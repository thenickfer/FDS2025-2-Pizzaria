package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Service
public class DescontoService {

    private static final int QNTD_PARA_DESCONTO = 3;
    private static final double TAXA = 0.07;
    private PedidoRepository pedidoRepository;

    @Autowired
    public DescontoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public double calcularDesconto(Pedido p) {
        // Se nao tem cliente, retorna 0
        if (p == null) {
            return 0;
        }

        // Pega a lisa de Pedidos do Cliente nso ultimso 20 dias
        List<Pedido> nroPedidos = pedidoRepository.ultimos20Dias(p.getCliente().getCpf()); // nao sei se vai ser getId
                                                                                           // ou getCPF, ver
        // depois isso

        // verifica tamanho da lista
        if (nroPedidos.size() > QNTD_PARA_DESCONTO) {
            double desc = p.getValor() * TAXA;
            p.setDesconto(desc);
            return desc;
        }

        p.setDesconto(0);

        return 0;
    }
}