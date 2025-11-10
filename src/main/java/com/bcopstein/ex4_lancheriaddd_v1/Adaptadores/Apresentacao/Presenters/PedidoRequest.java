package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Presenters;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequest {

    public static class ItemRequest {
        private long produtoId;
        private long quantidade;

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

    private String cpf;
    private List<ItemRequest> itens;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<ItemRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemRequest> itens) {
        this.itens = itens;
    }

}