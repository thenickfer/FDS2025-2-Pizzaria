package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;

import java.time.LocalDateTime;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

public class SubmetePedidoRequest {

    public static class ItemRequest {
        private long produtoId;
        private long quantidade;

        public ItemRequest() {
        }

        public ItemRequest(long produtoId, long quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }

        public long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(long produtoId) {
            this.produtoId = produtoId;
        }

        public long getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(long quantidade) {
            this.quantidade = quantidade;
        }
    }

    private String cliente;
    private List<ItemRequest> itens;

    public SubmetePedidoRequest(String cliente,
            List<ItemRequest> itens) {
        this.cliente = cliente;
        this.itens = itens;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<ItemRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemRequest> itens) {
        this.itens = itens;
    }

}