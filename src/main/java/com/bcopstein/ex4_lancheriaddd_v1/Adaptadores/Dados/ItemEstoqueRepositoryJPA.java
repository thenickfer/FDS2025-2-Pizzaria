package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueJPARepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ObjetosDB.ItemEstoqueBD;

@Primary
@Repository
public class ItemEstoqueRepositoryJPA implements ItemEstoqueRepository {
    private ItemEstoqueJPARepository repo;

    @Autowired
    public ItemEstoqueRepositoryJPA (ItemEstoqueJPARepository repo){
        this.repo = repo;   
    }

    @Override
    public List<ItemEstoque> getAll(){
        return repo.findAll().stream().map(ibd->ItemEstoqueBD.fromItemEstoqueBD(ibd)).toList();
    }

}
