package com.general.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.general.model.Note;
import com.general.model.Recette;
import com.general.model.User;


public interface NoteDao extends JpaRepository<Note, Long> {

	List<Note> findAllByuser(User user);
	List<Note> findAllByrecette(Recette recette);
	
	Note findByUserAndRecette(User user, Recette recette);
	
	Note findByidNote (int idNote);
	
	//List<Note> findAllByidUser(int idUser);
	

    @Query(value = "insert into Note (user,recette,note) VALUES (:user,:recette,:note)", nativeQuery = true)
    @Transactional
    void AddNote(@Param("user") int user, @Param("recette") int recette,@Param("note") int note);
	
}