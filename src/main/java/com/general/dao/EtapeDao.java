package com.general.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.general.model.Etape;
import com.general.model.Recette;


public interface EtapeDao extends JpaRepository<Etape, Long> {

	List<Etape> findAllByrecette(Recette recette);
	
	Etape findByidEtape(int idEtape);
}