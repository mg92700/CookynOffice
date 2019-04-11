package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.general.model.Recette;
import com.general.model.User;

public interface RecetteDao extends JpaRepository<Recette, Long> {

	@Query("SELECT r FROM Recette r WHERE r.libelleRecette = ?1")
	List<Recette> findAllWhereNom(String name);
	
	Recette findByIdRecette(int idRecette);
	
	List<Recette> findAllByLibelleRecette(String libelleRecette);

	List<Recette> findAllByUser(User user);
	
	
	@Query("SELECT r FROM Recette r WHERE r.libelleRecette LIKE %?1%")
	List<Recette> findAllByFiltre(String name);
	
	//@Query("SELECT r FROM Recette r WHERE r.creeparUser = ?1")
	//List<Recette> findAllCreerPar(User id);
	
}