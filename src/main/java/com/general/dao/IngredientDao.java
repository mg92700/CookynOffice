package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.general.model.Ingredient;

public interface IngredientDao extends JpaRepository<Ingredient, Long> {
    
	 @Query("SELECT l FROM Ingredient l WHERE l.libelleIngredient = ?1")
	    List<Ingredient> findAllWhereNom(String name);
	    
	    Ingredient findBylibelleIngredient(String libelleIngredient);
	    
	    @Query("SELECT l.libelleIngredient FROM Ingredient l WHERE l.catIngredient = ?1")
	    List<Ingredient> findAllWhereCat(String name);
	    
	    Ingredient findByidIngredient(int idIngredient);
	    
		@Query("SELECT DISTINCT i.catIngredient FROM Ingredient i")
		List<String> findAllDistinctCategorie();
}