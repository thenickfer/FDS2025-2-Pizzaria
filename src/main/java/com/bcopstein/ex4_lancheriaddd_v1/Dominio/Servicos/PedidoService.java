package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

public class PedidoService {
    private PedidoRepository pedidoRepository;
    private ItemEstoqueRepository itemEstoqueRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ItemEstoqueRepository itemEstoqueRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemEstoqueRepository = itemEstoqueRepository;

    }

    public Pedido submetePedido(Pedido ped) {

        List<ItemEstoque> todosItensEstoque = itemEstoqueRepository.getAll();
        Map<Long, ItemEstoque> estoqueMap = todosItensEstoque.stream()
                .collect(Collectors.toMap(
                        itemEstoque -> itemEstoque.getIngrediente().getId(),
                        itemEstoque -> itemEstoque));

        for (ItemPedido prod : ped.getItens()) {
            for (Ingrediente item : prod.getItem().getReceita().getIngredientes()) {
                ItemEstoque estoque = estoqueMap.get(item.getId());
                if (estoque == null || estoque.getQuantidade() < 1) {
                    ped.setStatus(Pedido.Status.AGUARDANDO);
                    return ped;
                }
            }
        }

        ped.setStatus(Pedido.Status.APROVADO);
        double val = ped.getValor();
        val -= val * ped.getDesconto();
        ped.setValorCobrado(val + val * ped.getImpostos());

        ped = pedidoRepository.submetePedido(ped);

        return ped;
    }

    public Pedido.Status getStatus(long id) {
        return pedidoRepository.getStatus(id);
    }

    public Boolean cancelarPedido(long id) {
        return pedidoRepository.cancelarPedido(id);
    }
}
