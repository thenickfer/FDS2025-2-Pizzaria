package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@Service
public class ProdutoService {
    ProdutosRepository produtosRepository;

    @Autowired
    public ProdutoService(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    public Map<Long, Produto> getMapping() {
        return this.produtosRepository.getAll().stream().collect(
                Collectors.toMap(produto -> produto.getId(), produto -> produto));
    }

}
