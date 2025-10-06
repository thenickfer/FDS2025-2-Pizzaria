package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private ItemEstoqueRepository itemEstoqueRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ItemEstoqueRepository itemEstoqueRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemEstoqueRepository = itemEstoqueRepository;

    }

    public Pedido submetePedido(Pedido ped) {

        if (ped == null || ped.getItens() == null || ped.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido must have at least one item");
        }

        List<ItemEstoque> todosItensEstoque = itemEstoqueRepository.getAll();
        Map<Long, ItemEstoque> estoqueMap = todosItensEstoque.stream()
                .collect(Collectors.toMap(
                        itemEstoque -> itemEstoque.getIngrediente().getId(),
                        itemEstoque -> itemEstoque));

        for (ItemPedido prod : ped.getItens()) {
            if (prod == null || prod.getItem() == null || prod.getItem().getReceita() == null) {
                throw new IllegalArgumentException("All items must have valid products and recipes");
            }

            for (Ingrediente item : prod.getItem().getReceita().getIngredientes()) {
                if (item == null)
                    continue;

                ItemEstoque estoque = estoqueMap.get(item.getId());
                if (estoque == null || estoque.getQuantidade() < 1) {
                    ped.setStatus(Pedido.Status.NEGADO);
                    return ped;
                }
            }
        }

        ped.setStatus(Pedido.Status.APROVADO);
        double val = ped.getItens().stream().map(i -> i.getItem()).mapToDouble(p -> p.getPreco()).sum();

        // add calc desconto e impostos

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
