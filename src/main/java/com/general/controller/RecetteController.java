
package com.general.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.ActualiteDao;
import com.general.dao.EtapeDao;
import com.general.dao.FavorisDao;
import com.general.dao.IngredientDao;
import com.general.dao.PlanningDao;
import com.general.dao.RecetteDao;
import com.general.dao.RecetteIngredientDao;
import com.general.dao.RelationDao;
import com.general.dao.UniteDao;
import com.general.dao.UserDao;
import com.general.dto.RecetteDto;
import com.general.dto.UserRecetteDeleteDto;
import com.general.model.Actualite;
import com.general.model.Etape;
import com.general.model.Favoris;
import com.general.model.Planning;
import com.general.model.Recette;
import com.general.model.RecetteIngredient;
import com.general.model.Unite;
import com.general.model.User;
import com.general.service.ApiService;
import com.general.service.ConvertUnite;
import com.general.service.CryptageService;
import com.general.service.ServiceImageFtp;

import java.util.stream.Stream;
@Controller
@RestController
@RequestMapping(value = "/recette")
public class RecetteController {
	
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
	IngredientDao ingredientDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UniteDao uniteDao;
	
	@Autowired 
	ServiceImageFtp serviceFtp;
	
	@Autowired 
	CryptageService cryptageService;
	
	@Autowired 
	ConvertUnite convert;
	
	@Autowired
	ActualiteDao actualiteDao;
	
	@Autowired
	PlanningDao planningDao;
	
	@Autowired
	FavorisDao favorisDao;
	
	
	
	@Autowired
	RelationDao relationDao;
	

	private String stats= new String();

	@RequestMapping(value = "/GetListRecette", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public List<Recette> GetListRecette()
	{
		List<Recette> recettes = recetteDao.findAll();
	

		
		return recettes;
	}
	
	@RequestMapping(value = "/GetListRecetteByOffSet/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListRecetteByOffSet(@PathVariable int offset)
	{
		List<Recette> recettes = recetteDao.findAll();
		List<Recette> recetteSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= recettes.size()) 
	        {
	        	recetteSub= recettes.subList(0, 0); //return empty.
	        }
	        if(offset>recettes.size())
	        {
	        	map.put("offset", recettes.size());
	        	map.put("listRecette", recetteSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	recetteSub= recettes.subList(offset, Math.min(offset+limite, recettes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	recetteSub= recettes.subList(offset, recettes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			recetteSub= recettes.subList(0, Math.min(limite, recettes.size()));
	    } else 
	    {
	    	recetteSub= recettes.subList(0, recettes.size());
	    }
		map.put("listRecette", recetteSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
		
	}
	
	@RequestMapping(value = "/DeleteRecetteById", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public String DeleteRecetteById(@RequestBody UserRecetteDeleteDto userRecetteDeleteDto)
	{
		
		Recette recette= recetteDao.findByIdRecette(userRecetteDeleteDto.getIdRecette());
		User  u = userDao.findUserByIdUser(userRecetteDeleteDto.getIdUser());
		
		if(u!=null)
		{
			if(u.getPasswordUser().equals(cryptageService.encrypt(userRecetteDeleteDto.getPassword()))) 
			{
			
					if(recette!=null) {
						
						
						
						Actualite actuCreate=actualiteDao.findCreateByUser(u, userRecetteDeleteDto.getIdRecette());
						actualiteDao.delete(actuCreate);
						
						
						Actualite actuFavoris=actualiteDao.findFavorisByUser(u, userRecetteDeleteDto.getIdRecette());
						actualiteDao.delete(actuFavoris);
						
						List<Etape> lstEtape=etapeDao.findAllByrecette(recette);
						List<Favoris> lstFavoris =favorisDao.findAllByRecette(recette);
						List<Planning> lstPlanning=planningDao.findPlanningByRecette(recette);
						List<RecetteIngredient> lstRecetteIngredient=recetteIngredientDao.findAllByrecette(recette);
						planningDao.delete(lstPlanning);
						favorisDao.delete(lstFavoris);
						etapeDao.delete(lstEtape);
						recetteIngredientDao.delete(lstRecetteIngredient);
						recetteDao.delete(recette);
						stats="Ok";
						
						
					}
					else {
						System.out.println(stats);
						stats="Recette inconnue";
						
					}
			}
			else 
			{
				
				stats="Mauvais mot de passe";
				System.out.println(stats);
			}
		}
		else
		{
			stats="Utilisateur inconnue";
			System.out.println(stats);
			
		}
		return stats;
	}
	
	@RequestMapping(value = "/GetListByRecette/{libelleRecette}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListByRecette(@PathVariable String libelleRecette, @PathVariable int offset)
	{
		List<Recette> recettes = recetteDao.findAllWhereNom(libelleRecette);
		List<Recette> recetteSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= recettes.size()) 
	        {
	        	recetteSub= recettes.subList(0, 0); //return empty.
	        }
	        if(offset>recettes.size())
	        {
	        	map.put("offset", recettes.size());
	        	map.put("listRecette", recetteSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	recetteSub= recettes.subList(offset, Math.min(offset+limite, recettes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	recetteSub= recettes.subList(offset, recettes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			recetteSub= recettes.subList(0, Math.min(limite, recettes.size()));
	    } else 
	    {
	    	recetteSub= recettes.subList(0, recettes.size());
	    }
		map.put("listRecette", recetteSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	
	@RequestMapping(value = "/GetRecetteById/{idRecette}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public RecetteDto GetRecetteById(@PathVariable int idRecette)
	{
		Recette r= recetteDao.findByIdRecette(idRecette);
		List<Etape> etapes=etapeDao.findAllByrecette(r);
		List<RecetteIngredient> ri=recetteIngredientDao.findAllByrecette(r);
		r.getUser().setPasswordUser(null);
//		List<Ingredient> ingredients=new ArrayList<Ingredient>();
//		for (RecetteIngredient recetteIngredient : ri) {
//			ingredients.add(recetteIngredient.getIngredient());
//		}
		RecetteDto recette=new RecetteDto();
		recette.setRecette(r);
		recette.setIngredients(ri);
		recette.setEtapes(etapes);
		return recette;
	}
	
	@RequestMapping(value = "/AddRecette", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public RecetteDto AddRecette(@RequestBody RecetteDto recetteDto) throws IOException 
	{
		RecetteDto recDto = new RecetteDto();
		List<Etape> etapes = new ArrayList<>();
		List<RecetteIngredient> ingredients = new ArrayList<>();
		User u = new User();
		Date d = java.util.Calendar.getInstance().getTime();
		if(recetteDto!=null)
		{
			
			u = userDao.findUserByIdUser(recetteDto.getRecette().getUser().getIdUser());
			recetteDto.getRecette().setUser(u);
			recetteDto.getRecette().setDateCreation(d);
			if(recetteDto.getImageRecette()!=null)
			{
				byte[] images = Base64.getDecoder().decode(recetteDto.getImageRecette());
				String url=serviceFtp.resultat(u.getUsernameUser(),recetteDto.getRecette().getLibelleRecette() ,images);
				recetteDto.getRecette().setUrlRecette(url);
			}
			recDto.setRecette(recetteDao.saveAndFlush(recetteDto.getRecette()));
		
			if(recetteDto.getEtapes()!= null) {				
				for(int i=0; i<recetteDto.getEtapes().size(); i++) {
					recetteDto.getEtapes().get(i).setRecette(recetteDto.getRecette());
					etapes.add(etapeDao.saveAndFlush(recetteDto.getEtapes().get(i)));
				}
				recDto.setEtapes(etapes);
			}
			if(recetteDto.getIngredients() != null) {		
				for(int i=0; i< recetteDto.getIngredients().size(); i++) {
					
					//rec.getIngredients().get(i).setQuantite(convert.Convert(rec.getIngredients().get(i).getUnite().getIdUnite(), rec.getIngredients().get(i).getQuantite()));
					recetteDto.getIngredients().get(i).setRecette(recetteDto.getRecette());
					recetteIngredientDao.saveAndFlush(recetteDto.getIngredients().get(i));
					ingredients.add(recetteDto.getIngredients().get(i));
				}
				recDto.setIngredients(ingredients);
			}
			
			//Add Actualité
			
			Actualite actualite=new Actualite();
			actualite.setDate(new Date());
			actualite.setIdWhat(recDto.getRecette().getIdRecette());
			actualite.setTypeActualite("Create");
			actualite.setUser(u);
			
			actualiteDao.saveAndFlush(actualite);
			
			//Add Actualité
			
			return recDto;
		}
		else
			return null;
	}
	
	@RequestMapping(value = "/UpdateRecette", method = RequestMethod.PUT,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Recette UpdateNote(@RequestBody Recette rec)
	{
		rec=recetteDao.saveAndFlush(rec);
		return rec;
	}
	
	@RequestMapping(value = "/GetListRecetteByFiltre/{filtre}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListRecetteByFiltre(@PathVariable String filtre, @PathVariable int offset)
	{

		List<Recette> recettes=recetteDao.findAllByFiltre(filtre);
		List<Recette> recetteSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= recettes.size()) 
	        {
	        	recetteSub= recettes.subList(0, 0); //return empty.
	        }
	        if(offset>recettes.size())
	        {
	        	map.put("offset", recettes.size());
	        	map.put("listRecette", recetteSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	recetteSub= recettes.subList(offset, Math.min(offset+limite, recettes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	recetteSub= recettes.subList(offset, recettes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			recetteSub= recettes.subList(0, Math.min(limite, recettes.size()));
	    } else 
	    {
	    	recetteSub= recettes.subList(0, recettes.size());
	    }
		map.put("listRecette", recetteSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	
	@RequestMapping(value = "/GetListRecetteCreatedByUser/{idU}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListRecetteCreatedByUser(@PathVariable int idU,@PathVariable int offset)
	{
		User u = new User();
		u.setIdUser(idU);
		List<Recette> recettes=recetteDao.findAllByUser(u);
		//List<Recette> rec = new ArrayList<>();
		
		List<Recette> recetteSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= recettes.size()) 
	        {
	        	recetteSub= recettes.subList(0, 0); //return empty.
	        }
	        if(offset>recettes.size())
	        {
	        	map.put("offset", recettes.size());
	        	map.put("listRecette", recetteSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	recetteSub= recettes.subList(offset, Math.min(offset+limite, recettes.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	recetteSub= recettes.subList(offset, recettes.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			recetteSub= recettes.subList(0, Math.min(limite, recettes.size()));
	    } else 
	    {
	    	recetteSub= recettes.subList(0, recettes.size());
	    }
		map.put("listRecette", recetteSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
	

}