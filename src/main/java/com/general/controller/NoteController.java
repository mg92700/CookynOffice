package com.general.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.NoteDao;
import com.general.dao.RecetteDao;
import com.general.dao.RecetteIngredientDao;
import com.general.dao.UserDao;
import com.general.model.Note;
import com.general.model.Planning;
import com.general.model.Recette;
import com.general.model.User;
import com.general.service.ApiService;
import com.general.service.CryptageService;


@Controller
@RestController
@RequestMapping(value = "/note")
public class NoteController {

	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	@Autowired
	NoteDao noteDao;
	@Autowired
	RecetteDao recetteDao;
	@Autowired
	RecetteIngredientDao recetteIngredientDao;
	@Autowired
	UserDao userDao;

	
	@Autowired 
	CryptageService cryptageService;

	@RequestMapping(value = "/GeListAllNotes/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GeListAllNotes(@PathVariable int offset)
	{
		List<Note> notes = noteDao.findAll();
		List<Note> notesSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= notes.size()) 
	        {
	        	notesSub= notes.subList(0, 0); //return empty.
	        }
	        if(offset>notes.size())
	        {
	        	map.put("offset", notes.size());
	        	map.put("listNotes", notesSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	notesSub= notes.subList(offset, Math.min(offset+limite, notes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	notesSub= notes.subList(offset, notes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			notesSub= notes.subList(0, Math.min(limite, notes.size()));
	    } else 
	    {
	    	notesSub= notes.subList(0, notes.size());
	    }
		map.put("listNotes", notesSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	
	
	
	@RequestMapping(value = "/GetListNotesByRecette/{idRecette}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListNotesByRecette(@PathVariable int idRecette, @PathVariable int offset)
	{
		Recette recette=new Recette();
		recette.setIdRecette(idRecette);
		List<Note> notes = noteDao.findAllByrecette(recette);
		List<Note> notesSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= notes.size()) 
	        {
	        	notesSub= notes.subList(0, 0); //return empty.
	        }
	        if(offset>notes.size())
	        {
	        	map.put("offset", notes.size());
	        	map.put("listNotes", notesSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	notesSub= notes.subList(offset, Math.min(offset+limite, notes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	notesSub= notes.subList(offset, notes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			notesSub= notes.subList(0, Math.min(limite, notes.size()));
	    } else 
	    {
	    	notesSub= notes.subList(0, notes.size());
	    }
		map.put("listNotes", notesSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	
	
	
	@RequestMapping(value = "/GetListNotesByUserId/{id}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListNotesByUserId(@PathVariable int id, @PathVariable int offset)
	{
		User user = new User();
		user.setIdUser(id);
		List<Note> notes = noteDao.findAllByuser(user);
		List<Note> notesSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= notes.size()) 
	        {
	        	notesSub= notes.subList(0, 0); //return empty.
	        }
	        if(offset>notes.size())
	        {
	        	map.put("offset", notes.size());
	        	map.put("listNotes", notesSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	notesSub= notes.subList(offset, Math.min(offset+limite, notes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	notesSub= notes.subList(offset, notes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			notesSub= notes.subList(0, Math.min(limite, notes.size()));
	    } else 
	    {
	    	notesSub= notes.subList(0, notes.size());
	    }
		map.put("listNotes", notesSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	
	@RequestMapping(value = "/AddNote", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Note AddNote(@RequestBody Note note)
	{
		Note noteDB= noteDao.findByUserAndRecette(note.getUser(), note.getRecette());
		Date dateCreation = null;
		
		if(noteDB!=null) {
			dateCreation = noteDB.getDateCreation();
			noteDao.delete(noteDB);
		}
		if(dateCreation!=null) {
			note.setDateCreation(dateCreation);
		}
		else {			
			note.setDateCreation(new Date());
		}
		note.setDateModification(new Date());
		note=noteDao.saveAndFlush(note);
		return note;
	}
	
}