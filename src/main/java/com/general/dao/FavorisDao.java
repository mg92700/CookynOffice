package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.general.model.Favoris;
import com.general.model.Note;
import com.general.model.Recette;
import com.general.model.User;


public interface FavorisDao extends JpaRepository<Favoris, Long> {
	
List<Favoris> findAllByUser(User user);	
	
	List<Favoris> findAllByRecette(Recette recette);	
	
	Favoris findByidFavoris(int idFavoris);
	
	
	@Query("SELECT f FROM Favoris f WHERE f.idFavoris = ?1")
	Favoris findnoteidFavoris(int idFav);
	
	Favoris findByUserAndRecette(User user, Recette recette);

}