package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ObjetosDB.ItemEstoqueBD;

public interface ItemEstoqueJPARepository extends JpaRepository <ItemEstoqueBD, Long>{
    //ja vem com findById(id),count(),findAll(),getOne(id) e muito mais
    
}
