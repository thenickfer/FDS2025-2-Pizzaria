package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;

import java.time.LocalDateTime;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;

public class SubmetePedidoRequest {

    public static class ItemRequest {
        private long produtoId;
        private int quantidade;

        public ItemRequest() {
        }

        public ItemRequest(long produtoId, int quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }

        public long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(long produtoId) {
            this.produtoId = produtoId;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }

    private Cliente cliente;
    private LocalDateTime dataHoraPagamento;
    private List<ItemRequest> itens;
    private double valor;
    private double impostos;
    private double desconto;

    public SubmetePedidoRequest(Cliente cliente, LocalDateTime dataHoraPagamento,
            List<ItemRequest> itens, double valor, double impostos, double desconto) {
        this.cliente = cliente;
        this.dataHoraPagamento = dataHoraPagamento;
        this.itens = itens;
        this.valor = valor;
        this.impostos = impostos;
        this.desconto = desconto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    public void setDataHoraPagamento(LocalDateTime dataHoraPagamento) {
        this.dataHoraPagamento = dataHoraPagamento;
    }

    public List<ItemRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemRequest> itens) {
        this.itens = itens;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getImpostos() {
        return impostos;
    }

    public void setImpostos(double impostos) {
        this.impostos = impostos;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}