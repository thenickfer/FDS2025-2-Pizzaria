package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.EstoqueService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private EstoqueService estoqueService;
    private ImpostoService impostoService;
    private DescontoService descontoService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, EstoqueService estoqueService,
            ImpostoService impostoService, DescontoService descontoService) {
        this.pedidoRepository = pedidoRepository;
        this.estoqueService = estoqueService;
        this.impostoService = impostoService;
        this.descontoService = descontoService;

    }

    public Pedido submetePedido(Pedido ped) {

        if (ped == null || ped.getItens() == null || ped.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido must have at least one item");
        }

        ped = estoqueService.avaliarPedido(ped);
        if (ped.getStatus() == Pedido.Status.NEGADO)
            return ped;

        double val = ped.getItens().stream().map(i -> i.getItem()).mapToDouble(p -> p.getPreco()).sum();
        ped.setValor(val);

        // add calc desconto e impostos
        val -= descontoService.calcularDesconto(ped);

        val += impostoService.calcularImposto(ped);

        ped.setValor(val);

        ped = pedidoRepository.submetePedido(ped);

        return ped;
    }

    public Pedido.Status getStatus(long id) {
        Pedido ped = pedidoRepository.pagarPedido(id);

        if (ped == null || ped.getStatus() == null){
            throw new IllegalArgumentException("pedido must exist");
        }

        return ped.getStatus();
    }

    public Boolean cancelarPedido(long id) {
        Boolean ped = pedidoRepository.cancelarPedido(id);

        if (!ped){
            throw new IllegalArgumentException("pedido must exist");
        }

        return true;
    }
}
