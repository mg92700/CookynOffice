package com.general.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Relation;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import com.general.dto.ActualiteDto;
import com.general.dto.CourseCategorieDto;
import com.general.dto.IngredientCourse;
import com.general.dto.CourseParamDto;
import com.general.dto.RelationUniteQuantiteDto;
import com.general.dto.UserDto;
import com.general.dto.WhoDto;
import com.general.model.Actualite;
import com.general.model.Favoris;
import com.general.model.Ingredient;
import com.general.model.Planning;
import com.general.model.Recette;
import com.general.model.RecetteIngredient;
import com.general.model.User;
import com.general.security.TokenSecurity;
import com.general.service.CryptageService;
import com.google.api.client.json.Json;

@Controller
@RestController
@RequestMapping(value = "/actualite")
public class ActualiteController {
	
	@Autowired
	ActualiteDao actualiteDao;
	 
	@Autowired
	UserDao userDao;
	
	@Autowired
	RecetteDao recetteDao;
	
	@Autowired
	RelationDao relationDao;
	
	    
	

	
	@RequestMapping(value = "/GetActualiteByUser/{idUser}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetActualiteByUser(@PathVariable int idUser,@PathVariable int offset)
	{
		Map<String, Object> map = new HashMap<>(); 
		List<ActualiteDto> actulalitesSub=  new ArrayList<>();
		List<ActualiteDto> actualitesDto = new ArrayList<>();
		User user=userDao.findUserByIdUser(idUser);
			if(user!=null) 
			{
				List<com.general.model.Relation> lstRelationUser=relationDao.findAllById(user);
				List<User> lstuserFriend=  new ArrayList<>();
				for(com.general.model.Relation r:lstRelationUser)
				{
					
					lstuserFriend.add(r.getFriend());
					
				}
			if(lstuserFriend.size()>0)
			{
					List<Actualite> actulalites = actualiteDao.findAllByFriend(lstuserFriend);
					for(Actualite uneActualite :actulalites)
					{
						User unUserAction =userDao.findUserByIdUser(idUser);
						if (unUserAction!=null)
						{
							unUserAction.setPasswordUser("");
							unUserAction.setRole("");
					
							
							ActualiteDto uneActualiteDto = new ActualiteDto();
							uneActualiteDto.setIdActualite(uneActualite.getIdActualite());
							uneActualiteDto.setDate(uneActualite.getDate());
							
							User unUserAmies=userDao.findUserByIdUser(uneActualite.getUser().getIdUser());
							
							uneActualiteDto.setWhat(unUserAmies);
							
							uneActualiteDto.setTypeActualite(uneActualite.getTypeActualite());
							
							WhoDto whoDto = new WhoDto();
							String getActu=uneActualite.getTypeActualite();
							if(getActu.equals("Create"))
							{
								Recette r=recetteDao.findByIdRecette(uneActualite.getIdWhat());
								if(r!=null)
								{
									whoDto.setId(r.getIdRecette());
									whoDto.setName(r.getLibelleRecette());
									whoDto.setType("recette");
								}
								
							}
							if(getActu.equals("Favoris"))
							{
								Recette r=recetteDao.findByIdRecette(uneActualite.getIdWhat());
								if(r!=null)
								{
									whoDto.setId(r.getIdRecette());
									whoDto.setName(r.getLibelleRecette());
									whoDto.setType("recette");
								}
								
							}
							if(getActu.equals("Follow"))
							{
								User u=userDao.findUserByIdUser(uneActualite.getIdWhat());
								if(u!=null)
								{
									whoDto.setId(u.getIdUser());
									whoDto.setName(u.getUsernameUser());
									whoDto.setType("user");
								}
								
							}
							uneActualiteDto.setWhoDto(whoDto);
							
							actualitesDto.add(uneActualiteDto);
							
						}
						
					}
				}
			}
	
			int limite=20;
			
			if (offset>0) 
			{
				
		        if (offset >= actualitesDto.size()) 
		        {
		        	actulalitesSub= actualitesDto.subList(0, 0); //return empty.
		        }
		        if(offset>actualitesDto.size())
		        {
		        	map.put("offset", actualitesDto.size());
		        	map.put("listActu", actulalitesSub);
		        	map.put("limite", limite);
		        	return map;
		        	
		        }
		        if (2 >-1) 
		        {
		            //apply offset and limit
		        	actulalitesSub= actualitesDto.subList(offset, Math.min(offset+limite, actualitesDto.size()));
		        } 
		        else 
		        {
		            //apply just offset
		        	actulalitesSub= actualitesDto.subList(offset, actualitesDto.size());
		        }
		        
		    } 
			else if (2 >-1) 
			{
		        //apply just limit
				actulalitesSub= actualitesDto.subList(0, Math.min(limite, actualitesDto.size()));
		    } else 
		    {
		    	actulalitesSub= actualitesDto.subList(0, actualitesDto.size());
		    }
			map.put("listActu", actulalitesSub);
			map.put("offset", offset);
			map.put("limite", limite);
			return map;
	}
	
}