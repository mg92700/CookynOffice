package com.general.controller;

import java.util.ArrayList;
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

import com.general.dao.EtapeDao;
import com.general.dao.RecetteDao;
import com.general.dao.RecetteIngredientDao;
import com.general.model.Etape;
import com.general.model.Recette;
import com.general.service.ApiService;
import com.general.service.CryptageService;

@Controller
@RestController
@RequestMapping(value = "/etape")
public class EtapeController {

	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	@Autowired
	EtapeDao etapeDao;
	@Autowired
	RecetteDao recetteDao;
	@Autowired
	RecetteIngredientDao recetteIngredientDao;
	
	@Autowired 
	CryptageService cryptageService;

	@RequestMapping(value = "/GetListEtapesById/{idRecette}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListEtapesById(@PathVariable int idRecette, @PathVariable int offset)
	{
		Recette recette=new Recette();
		recette.setIdRecette(idRecette);
		List<Etape> etapes = etapeDao.findAllByrecette(recette);
		List<Etape> etapesSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= etapes.size()) 
	        {
	        	etapesSub= etapes.subList(0, 0); //return empty.
	        }
	        if(offset>etapes.size())
	        {
	        	map.put("offset", etapes.size());
	        	map.put("listEtape", etapesSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	etapesSub= etapes.subList(offset, Math.min(offset+limite, etapes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	etapesSub= etapes.subList(offset, etapes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			etapesSub= etapes.subList(0, Math.min(limite, etapes.size()));
	    } else 
	    {
	    	etapesSub= etapes.subList(0, etapes.size());
	    }
		map.put("listEtape", etapesSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	
	
	@RequestMapping(value = "/AddEtapes", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public List<Etape> AddEtapes(@RequestBody List<Etape> etapes)
	{
		for (Etape etape : etapes) {
			etapeDao.saveAndFlush(etape);
		}
		return etapes;
	}
	
	@RequestMapping(value = "/UpdateEtape", method = RequestMethod.PUT,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Etape UpdateEtape(@RequestBody Etape etape)
	{
			etapeDao.saveAndFlush(etape);
			return etape;
	}

	
}