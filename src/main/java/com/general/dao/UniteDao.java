package com.general.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.general.model.Unite;

public interface UniteDao extends JpaRepository<Unite, Long> {
	Unite findBylibelleUnite(String libelleUnite);	
	Unite findByidUnite(int idUnite);	
}
