package com.general.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.ActualiteDao;
import com.general.dao.FavorisDao;
import com.general.dao.RecetteDao;
import com.general.dao.RelationDao;
import com.general.dao.UserDao;
import com.general.dto.AmieDto;
import com.general.dto.RelationDto;
import com.general.model.Actualite;
import com.general.model.Recette;
import com.general.model.Relation;
import com.general.model.User;
import com.general.service.ApiService;
import com.general.service.CryptageService;

@Controller
@RestController
@RequestMapping(value = "/relation")
public class RelationController {
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RecetteDao recetteDao;
	
	@Autowired
	FavorisDao favorisDao;
	
	@Autowired
	RelationDao relationDao;
	
	@Autowired 
	CryptageService cryptageService;
	
	@Autowired
	ActualiteDao actualiteDao;
	
	
	
	@RequestMapping(value = "/GetRelation/{idUser}/{idAmis}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Boolean GetRelation(@PathVariable int idUser,@PathVariable int idAmis)
	{
		User user = userDao.findUserByIdUser(idUser);
		User friend =userDao.findUserByIdUser(idAmis);
		
		if(user!=null && friend!=null)
		{
			List<Relation> r=relationDao.findAllByIdUserAndIdFriend(user,friend);
			if(r.size()>0)
			{
				return true;
				
			}
		}
	
		return false;
	}
	
	
	@RequestMapping(value = "/CreateRelation/{idUser}/{idFriend}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public RelationDto CreateRelation(@PathVariable Integer idUser,@PathVariable Integer idFriend)
	{
		Date date = new Date(System.currentTimeMillis());
		User user = userDao.findUserByIdUser(idUser);
		User friend = userDao.findUserByIdUser(idFriend);
		Relation r = new Relation();
		RelationDto rela = new RelationDto();
		if(user!=null && friend!=null) {			
			
			r.setFriend(friend);
			r.setUser(user);
			r.setDateRelation(date);
			r = relationDao.saveAndFlush(r);
			rela = (RelationDto) JTransfo.convert(r);
			
			//Add Actualité
			
			Actualite actualite=new Actualite();
			actualite.setDate(date);
			actualite.setIdWhat(idFriend);
			actualite.setTypeActualite("Follow");
			actualite.setUser(user);
			
			actualiteDao.saveAndFlush(actualite);
			
			//Add Actualité

		}else {
			rela.setErrorTxt("Un des Users n'existe pas dans la base");
		}

		return rela;
		
	}
	
	
	@RequestMapping(value = "/GetListAbonne/{idUser}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public  Map<String, Object> GetListAbonne(@PathVariable Integer idUser, @PathVariable int offset)
	{
		
		List<AmieDto> listFriend = new ArrayList<AmieDto>();
		if(idUser!=null)
		{
			User userConnecter= userDao.findUserByIdUser(idUser);
			List<Relation> listR=relationDao.findAllByFriend(userConnecter);
			for (int i=0; i< listR.size(); i++) {
				AmieDto friendDto= new AmieDto();
			    User userFriend=userDao.findUserByIdUser(listR.get(i).getUser().getIdUser());
			    friendDto.setIdUser(userFriend.getIdUser());
			    friendDto.setNomUser(userFriend.getNomUser());
			    friendDto.setPrenomUser(userFriend.getPrenomUser());
			    friendDto.setUsernameUser(userFriend.getUsernameUser());
			    listFriend.add(friendDto);
				}
		}
		
		List<AmieDto> listFriendSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= listFriend.size()) 
	        {
	        	listFriendSub= listFriend.subList(0, 0); //return empty.
	        }
	        if(offset>listFriend.size())
	        {
	        	map.put("offset", listFriend.size());
	        	map.put("listRelation", listFriendSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	listFriendSub= listFriend.subList(offset, Math.min(offset+limite, listFriend.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	listFriendSub= listFriend.subList(offset, listFriend.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			listFriendSub= listFriend.subList(0, Math.min(limite, listFriend.size()));
	    } else 
	    {
	    	listFriendSub= listFriend.subList(0, listFriend.size());
	    }
		map.put("listRelation", listFriendSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
		
	}
	
	
	@RequestMapping(value = "/GetListAbonnement/{idUser}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public  Map<String, Object> GetListAbonnement(@PathVariable Integer idUser, @PathVariable int offset)
	{
		List<AmieDto> listFriend = new ArrayList<AmieDto>();
		if(idUser!=null)
		{
			User userConnecter= userDao.findUserByIdUser(idUser);
			List<Relation> listR=relationDao.findAllByUser(userConnecter);
			for (int i=0; i< listR.size(); i++) {
				AmieDto friendDto= new AmieDto();
			    User userFriend=userDao.findUserByIdUser(listR.get(i).getFriend().getIdUser());
			    friendDto.setIdUser(userFriend.getIdUser());
			    friendDto.setNomUser(userFriend.getNomUser());
			    friendDto.setPrenomUser(userFriend.getPrenomUser());
			    friendDto.setUsernameUser(userFriend.getUsernameUser());
			    
			    listFriend.add(friendDto);
				}
		}
		
		List<AmieDto> listFriendSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=20;
		
		if (offset>0) 
		{
			
	        if (offset >= listFriend.size()) 
	        {
	        	listFriendSub= listFriend.subList(0, 0); //return empty.
	        }
	        if(offset>listFriend.size())
	        {
	        	map.put("offset", listFriend.size());
	        	map.put("listRelation", listFriendSub);
	        	map.put("limite", limite);
	        	return map;
	        	
	        }
	        if (2 >-1) 
	        {
	            //apply offset and limit
	        	listFriendSub= listFriend.subList(offset, Math.min(offset+limite, listFriend.size()));
	        } 
	        else 
	        {
	            //apply just offset
	        	listFriendSub= listFriend.subList(offset, listFriend.size());
	        }
	        
	    } 
		else if (2 >-1) 
		{
	        //apply just limit
			listFriendSub= listFriend.subList(0, Math.min(limite, listFriend.size()));
	    } else 
	    {
	    	listFriendSub= listFriend.subList(0, listFriend.size());
	    }
		map.put("listRelation", listFriendSub);
		map.put("offset", offset);
		map.put("limite", limite);
		return map;
		
	}
	
	

	@RequestMapping(value = "/DeleteRelation/{idUser}/{idFriend}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Boolean DeleteRelation(@PathVariable Integer idUser, @PathVariable Integer idFriend)
	{
		User user = userDao.findUserByIdUser(idUser);
		User friend = userDao.findUserByIdUser(idFriend);
		List<Relation> r = new ArrayList<>();
		Boolean response = false;
		if(user!=null && friend!=null) {			
			
			r = relationDao.findAllByFriendAndUser(friend, user);
//			for(int i=0; i< r.size(); i++) {
//				relationDao.delete(r.get(i));
//			}
			relationDao.delete(r);
			
			Actualite actu = actualiteDao.findFollowByUser(user, idFriend);
			actualiteDao.delete(actu);
			
			response = true;
		}

		return response;
		
	}

}