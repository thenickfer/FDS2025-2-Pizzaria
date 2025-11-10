package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ObjetosDB.IngredienteBD;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IngredientesJPARepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IngredientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;

@Primary
@Repository
public class IngredientesRepositoryJPA implements IngredientesRepository{
    private IngredientesJPARepository repo;

    @Autowired
    public IngredientesRepositoryJPA (IngredientesJPARepository repo){
        this.repo = repo;
    }

	@Override
	public List<Ingrediente> recuperaTodos() {
        return repo.findAll().stream().map(ibd->IngredienteBD.fromIngredienteBD(ibd)).toList();
	}

	@Override
	public List<Ingrediente> recuperaIngredientesReceita(long receitaId) {
		return repo.findByReceitasId(receitaId).stream().map(IngredienteBD::fromIngredienteBD).toList();
	}

    
}
