package com.general.controller.admin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.general.dao.NoteDao;
import com.general.dao.PlanningDao;
import com.general.dao.RecetteDao;
import com.general.dao.RecetteIngredientDao;
import com.general.dao.RelationDao;
import com.general.dao.UniteDao;
import com.general.dao.UserDao;
import com.general.dto.UserDto;
import com.general.model.Actualite;
import com.general.model.Etape;
import com.general.model.Favoris;
import com.general.model.Ingredient;
import com.general.model.Note;
import com.general.model.Planning;
import com.general.model.Recette;
import com.general.model.RecetteIngredient;
import com.general.model.Relation;
import com.general.model.Unite;
import com.general.model.User;

import com.general.security.TokenSecurity;
import com.general.service.CryptageService;

@Controller
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	RecetteDao recetteDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired 
	CryptageService cryptageService;
	
	@Autowired
	TokenSecurity t;
    
    @Autowired
    IngredientDao ingredientDao;
    
    @Autowired
    FavorisDao favorisDao;
    
    @Autowired
    UniteDao uniteDao;
    
    @Autowired
    PlanningDao planningDao;
    
    
    @Autowired
    RelationDao relationDao;
    
    @Autowired
    EtapeDao etapeDao;
    
    @Autowired
    NoteDao noteDao;
    
    @Autowired
    ActualiteDao actualiteDao;
    
    @Autowired
    RecetteIngredientDao recetteIngredientDao;
    
	@RequestMapping(value = "/LogAdmin", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")

	public Map<String, Object> LogAdmin(@RequestBody UserDto user,HttpServletRequest request)
	{
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
		String token=null;
        
      
		Boolean trouver=false;
		String mdpEncore=cryptageService.encrypt(user.getPasswordUser());
		User u= userDao.findByMailUser(user.getMailUser());
		if(u!=null)
		{
			if(u.getRole().equals("admin")) 
			{
				if(mdpEncore.equals(u.getPasswordUser()))
				{
					trouver=true;
					token=t.getToken();
					
				}
			}
		}
		
		mapReturn.put("EstConnecter", trouver);
		mapReturn.put("Token", token);
		
		return mapReturn;
		
	}

	@RequestMapping(value = "/GetlistUsersByOffSet/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetlistUsersByOffSet(@PathVariable int offset)
	{
		List<User> users = userDao.findAll();
		List<User> userSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= users.size()) 
	        {
	        	userSub= users.subList(0, 0); //return empty.
	        }
	        if(offset>users.size())
	        {
	        	map.put("offset", users.size());
	        	map.put("listUser", userSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	userSub= users.subList(offset, Math.min(offset+limite, users.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	userSub= users.subList(offset, users.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
	    	userSub= users.subList(0, Math.min(limite, users.size()));
	    } else 
	    {
	    	userSub= users.subList(0, users.size());
	    }
		map.put("listUser", userSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
		
		
	}
	
	@RequestMapping(value = "/DeleteUserById/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public User DeleteUserById(@PathVariable int idUser)
	{
		User u = userDao.findUserByIdUser(idUser);

		UserDto userReturn = new UserDto();
	
		if(u!= null) 
		{
			int idUserCourant=u.getIdUser();
			
			
			//Relation supprime tous
			//Actualite supprimer tous
			//Planning supprimer tous
			List<Favoris> listFavoris= favorisDao.findAllByUser(u);
			favorisDao.delete(listFavoris);
			
			List<Relation> listRelationUser= relationDao.findAllByUser(u);
			relationDao.delete(listRelationUser);
			
			List<Relation> listRelationUserFriend=relationDao.findAllByFriend(u);
			relationDao.delete(listRelationUserFriend);
			
			List<Actualite> listActulite = actualiteDao.findAllByuser(u);
			actualiteDao.delete(listActulite);
			
			List<Planning> listPlanning= planningDao.findAllByuser(u);
			planningDao.delete(listPlanning);
			
			User userDefault = userDao.findUserByIdUser(99999);
			List<Recette> listRecette=  recetteDao.findAllByUser(u);
			for(Recette uneRecette : listRecette)
			{
				uneRecette.setUser(userDefault);
			

			}
			
			
		
			userDao.delete(u);
			recetteDao.save(listRecette);
			
		}
		else 
		{
		userReturn.setErrortxt("User est inconnue");
		}
		return u;
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
	
	@RequestMapping(value = "/DeleteRecetteById/{idRecette}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Recette DeleteRecetteById(@PathVariable int idRecette)
	{
		Recette recette= recetteDao.findByIdRecette(idRecette);
		if(recette!=null) {
			List<Etape> lstEtape=etapeDao.findAllByrecette(recette);
			List<Favoris> lstFavoris =favorisDao.findAllByRecette(recette);
			List<Planning> lstPlanning=planningDao.findPlanningByRecette(recette);
			List<RecetteIngredient> lstRecetteIngredient=recetteIngredientDao.findAllByrecette(recette);
			planningDao.delete(lstPlanning);
			favorisDao.delete(lstFavoris);
			etapeDao.delete(lstEtape);
			recetteIngredientDao.delete(lstRecetteIngredient);
			recetteDao.delete(recette);
			
		}
		else {
			System.out.println("Recette inconnue");
		}
		return recette;
	}
	
    @RequestMapping(value = "/GetListAllIngredient/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
    @CrossOrigin(origins = "*")
    public Map<String, Object> GetListAllIngredient(@PathVariable int offset)
    {
    	
    	 List<Ingredient> ingredients = ingredientDao.findAll();
    	 List<Ingredient> ingredientsSub = new ArrayList<>();
 		Map<String, Object> map = new HashMap<>(); 
 		//return recettes;
 		int limite=20;
 		
 		if (offset>0) 
 		{
 			
 	        if (offset >= ingredients.size()) 
 	        {
 	        	ingredientsSub= ingredients.subList(0, 0); //return empty.
 	        }
 	        if(offset>ingredients.size())
 	        {
 	        	map.put("offset", ingredients.size());
 	        	map.put("listIngredients", ingredientsSub);
 	        	map.put("limite", limite);
 	        	return map;
 	        	
 	        }
 	        if (2 >-1) 
 	        {
 	            //apply offset and limit
 	        	ingredientsSub= ingredients.subList(offset, Math.min(offset+limite, ingredients.size()));
 	        } 
 	        else 
 	        {
 	            //apply just offset
 	        	ingredientsSub= ingredients.subList(offset, ingredients.size());
 	        }
 	        
 	    } 
 		else if (2 >-1) 
 		{
 	        //apply just limit
 			ingredientsSub= ingredients.subList(0, Math.min(limite, ingredients.size()));
 	    } else 
 	    {
 	    	ingredientsSub= ingredients.subList(0, ingredients.size());
 	    }
 		map.put("listIngredients", ingredientsSub);
 		map.put("offset", offset);
 		map.put("limite", limite);
 		return map;
    }
    
    @RequestMapping(value = "/DeleteIngredientById/{idIngredient}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Ingredient DeleteIngredientById(@PathVariable int idIngredient)
	{
    	Ingredient i= ingredientDao.findByidIngredient(idIngredient);
		if(i!=null) {
			ingredientDao.delete(i);
		}
		else {
			System.out.println("Ingredient inconnue");
		}
		return i;
	}
    
    @RequestMapping(value = "/GetListUnites/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object>  GetListUnites(@PathVariable int offset)
	{
		List<Unite> unite = uniteDao.findAll();
		List<Unite> uniteSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= unite.size()) 
	        {
	        	uniteSub= unite.subList(0, 0); //return empty.
	        }
	        if(offset>unite.size())
	        {
	        	map.put("offset", unite.size());
	        	map.put("listUnite", uniteSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	uniteSub= unite.subList(offset, Math.min(offset+limite, unite.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	uniteSub= unite.subList(offset, unite.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			uniteSub= unite.subList(0, Math.min(limite, unite.size()));
	    } else 
	    {
	    	uniteSub= unite.subList(0, unite.size());
	    }
		map.put("listUnite", uniteSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
	}
    
	@RequestMapping(value = "/DeleteById/{idUnite}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Unite DeleteteById(@PathVariable int idUnite)
	{
		Unite unite = uniteDao.findByidUnite(idUnite);
		
		if(unite!=null) {
			uniteDao.delete(unite);
		}
		else {
			System.out.println("id inexistant");
		}
		return unite;
	}
	
    @RequestMapping(value = "/GetListPlanningsByOffset/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListPlanningsByOffset(@PathVariable int offset)
	{
		List<Planning> planning = planningDao.findAll();
		List<Planning> planningSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= planning.size()) 
	        {
	        	planningSub= planning.subList(0, 0); //return empty.
	        }
	        if(offset>planning.size())
	        {
	        	map.put("offset", planning.size());
	        	map.put("listPlanning", planningSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	planningSub= planning.subList(offset, Math.min(offset+limite, planning.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	planningSub= planning.subList(offset, planning.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			planningSub= planning.subList(0, Math.min(limite, planning.size()));
	    } else 
	    {
	    	planningSub= planning.subList(0, planning.size());
	    }
		map.put("listPlanning", planningSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
			
	}
    
    @RequestMapping(value = "/DeletePlanningById/{idPlanning}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Planning DeletePlanningById(@PathVariable int idPlanning)
	{
		Planning p= planningDao.findByidPlanning(idPlanning);
		if(p!=null) {
			planningDao.delete(p);
		}
		else {
			System.out.println("Planning inconnue");
		}
		return p;
	}
    
    @RequestMapping(value = "/GetListAllNotes/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
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
    
    @RequestMapping(value = "/DeleteNoteById/{idNote}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Note DeleteNoteById(@PathVariable int idNote)
	{
		Note n= noteDao.findByidNote(idNote);
		if(n!=null) {
			noteDao.delete(n);
		}
		else {
			System.out.println("Note inconnue");
		}
		return n;
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
    
	@RequestMapping(value = "/DeleteFavorisById/{idFavoris}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Favoris DeleteFavorisById(@PathVariable int idFavoris)
	{
		Favoris f= favorisDao.findByidFavoris(idFavoris);
		if(f!=null) {
			favorisDao.delete(f);
		}
		else {
			System.out.println("Favoris inconnue");
		}
		return f;
	}
	
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
    
	@RequestMapping(value = "/DeleteEtapeById/{idEtape}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Etape DeleteEtapeById(@PathVariable int idEtape)
	{
		Etape e= etapeDao.findByidEtape(idEtape);
		if(e!=null) {
			etapeDao.delete(e);
		}
		else {
			System.out.println("Etape inconnue");
		}
		return e;
	}
 
	@RequestMapping(value = "/DeconnectionUser/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Boolean DeconnectionUser(@PathVariable int idUser)
	{
		User userDb = userDao.findUserByIdUser(idUser);
		User user =null;
		if(userDb!=null)
		{
			user=userDb;
			user.setCompteActive(0);
			userDao.saveAndFlush(user);
			return true;
			
		}
		return false;
		
	}
}