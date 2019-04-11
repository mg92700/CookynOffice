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

import com.general.dao.ActualiteDao;
import com.general.dao.FavorisDao;
import com.general.dao.RecetteDao;
import com.general.dao.UserDao;
import com.general.model.Actualite;
import com.general.model.Favoris;
import com.general.model.Recette;
import com.general.model.User;
import com.general.service.ApiService;
import com.general.service.CryptageService;

@Controller
@RestController
@RequestMapping(value = "/favoris")
public class FavorisController {

	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	@Autowired
	FavorisDao favorisDao;
	@Autowired
	UserDao userDao;
	
	@Autowired
	RecetteDao recetteDao;
	
	@Autowired 
	CryptageService cryptageService;
	
	@Autowired
	ActualiteDao actualiteDao;

	@RequestMapping(value = "/GetlistFavorisByUser/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public List<Favoris> GetlistFavorisByUser(@PathVariable int idUser)
	{
		User user=new User();
		user.setIdUser(idUser);
		List<Favoris> favoris = favorisDao.findAllByUser(user);
		return favoris;
	}
	
	
	
	@RequestMapping(value = "/ExistFavoris/{idUser}/{idRecette}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Boolean ExistFavoris(@PathVariable int idUser,@PathVariable int idRecette)
	{
	
		
		Recette r= recetteDao.findByIdRecette(idRecette);
		User u =userDao.findUserByIdUser(idUser);
		
		if(u!=null)
		{
			if(r!=null)
			{
				
				Favoris f = favorisDao.findByUserAndRecette(u, r);
				if(f!=null)
				{
					return true;
					
				}
				
			}

		}
		
		return false;
	}
	
	@RequestMapping(value = "/GetlistFavorisByUser/{idUser}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetlistFavorisByUser(@PathVariable int idUser,@PathVariable int offset)
	{
		Map<String, Object> map = new HashMap<>(); 
		User user=new User();
		user.setIdUser(idUser);
		List<Favoris> favoris = favorisDao.findAllByUser(user);
		List<Favoris> favorisSub=  new ArrayList<>();
		
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= favoris.size()) 
	        {
	        	favorisSub= favoris.subList(0, 0); //return empty.
	        }
	        if(offset>favoris.size())
	        {
	        	map.put("offset", favoris.size());
	        	map.put("listFavoris", favorisSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	favorisSub= favoris.subList(offset, Math.min(offset+limite, favoris.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	favorisSub= favoris.subList(offset, favoris.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			favorisSub= favoris.subList(0, Math.min(limite, favoris.size()));
	    } else 
	    {
	    	favorisSub= favoris.subList(0, favoris.size());
	    }
		map.put("listFavoris", favorisSub);
		map.put("offset", offset);
		map.put("limite", limite);
		
		
		return map;
	}
	
	@RequestMapping(value = "/AddFavoris/{idRecette}/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Favoris AddFavoris(@PathVariable int idRecette, @PathVariable int idUser)
	{
		Favoris favoris = new Favoris();
		Recette recette = recetteDao.findByIdRecette(idRecette);
		User user = userDao.findUserByIdUser(idUser);
		favoris.setRecette(recette);
		favoris.setUser(user);
		favoris.setDateCreation(new Date());
		favorisDao.saveAndFlush(favoris);
		
		//Add Actualité
		
		Actualite actualite=new Actualite();
		actualite.setDate(new Date());
		actualite.setIdWhat(recette.getIdRecette());
		actualite.setTypeActualite("Favoris");
		actualite.setUser(user);
		
		actualiteDao.saveAndFlush(actualite);
		
		//Add Actualité
		
		return favoris;
	}
	
	@RequestMapping(value = "/RemoveFavoris/{idUser}/{idRecette}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Favoris RemoveFavoris(@PathVariable int idUser, @PathVariable int idRecette)
	{
		User user = new User();
		user.setIdUser(idUser);
		Recette recette = new Recette();
		recette.setIdRecette(idRecette);
		
		Favoris favoris = favorisDao.findByUserAndRecette(user, recette);
		favorisDao.delete(favoris);
		Actualite actuFavoris=actualiteDao.findFavorisByUser(user, recette.getIdRecette());
		actualiteDao.delete(actuFavoris);
		return favoris;
	}


}