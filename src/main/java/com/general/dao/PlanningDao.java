package com.general.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.general.model.Planning;
import com.general.model.Recette;
import com.general.model.User;

public interface PlanningDao extends JpaRepository<Planning, Long> {
	
List<Planning> findAllByuser(User user);
	
	Planning findByidPlanning(int idplanning);
	
	//p.user = :user and
	
	@Query("SELECT p FROM Planning p WHERE p.user = :user and p.datePlanning BETWEEN :dateDebut and :dateFin")
    List<Planning> findPlanningByUserAndDate(@Param(value = "user") User user, @Param(value = "dateDebut") Date dateDebut,
            @Param(value = "dateFin") Date dateFin);
	

	@Query("SELECT p FROM Planning p WHERE p.user = :user AND p.datePlanning = :date")
    List<Planning> findPlanningByDate(@Param(value = "user") User user, @Param(value = "date") Date date);
	
	@Query("SELECT p FROM Planning p WHERE p.user = :user and  Month(p.datePlanning) = :Month and Year(p.datePlanning)=:YEAR")
    List<Planning> findPlanningByMouthYears(@Param(value = "user") User user, @Param(value = "Month") int Month,@Param(value = "YEAR") int YEAR);
	
	
	@Query("SELECT p FROM Planning p WHERE p.recette = :recette ")
    List<Planning> findPlanningByRecette(@Param(value = "recette") Recette recette);
	
	
}
