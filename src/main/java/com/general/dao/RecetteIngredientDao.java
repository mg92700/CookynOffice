package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.general.model.Recette;
import com.general.model.RecetteIngredient;


public interface RecetteIngredientDao extends JpaRepository<RecetteIngredient, Long> {

	List<RecetteIngredient> findAllByrecette(Recette recette);

}