package com.general.controller;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.PlanningDao;
import com.general.dao.RecetteDao;
import com.general.dao.UserDao;

import com.general.dto.PlanningUserDto;
import com.general.model.Planning;
import com.general.model.Recette;
import com.general.model.User;
import com.general.service.ApiService;
import com.general.service.CryptageService;

@Controller
@RestController
@RequestMapping(value = "/planning")

public class PlanningController {

	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	@Autowired
	PlanningDao planningDao;
	
	@Autowired 
	CryptageService cryptageService;
	
	@Autowired
	UserDao userdao;
	
	@Autowired
	RecetteDao recettedao;
	
	@RequestMapping(value = "/GetListPlanningsByUserOffset/{idUser}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetListPlanningsByUserOffset(@PathVariable int idUser, @PathVariable int offset)
	{
		User user =new User();
		user.setIdUser(idUser);
		List<Planning> planning=planningDao.findAllByuser(user);
		List<Planning> planningSub = new ArrayList<>();
		Map<String, Object> map = new HashMap<>(); 
		//return recettes;
		int limite=200;
		
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
	
	@RequestMapping(value = "/AddPlanning", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Planning AddPlanning(@RequestBody Planning planning)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		if(planning!=null)
		{
			String s=dateFormat.format(planning.getDatePlanning());
			Date d = new Date(s+" 00:00:00");
			Planning thePlanning = new Planning();
			thePlanning.setDatePlanning(d);
			thePlanning.setRecette(planning.getRecette());
			thePlanning.setUser(planning.getUser());
			Planning thePlanningCourrant = planningDao.saveAndFlush(thePlanning);
					System.out.println( "date:" +d);
			return thePlanningCourrant;
		}
		else
			return null;
	}
	
	@RequestMapping(value = "/UpdatePlanning", method = RequestMethod.PUT,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Planning UpdatePlanning(@RequestBody Planning planning)
	{
		planning=planningDao.saveAndFlush(planning);
		return planning;
	}
	
	@RequestMapping(value = "/GetListPlanningByUserAndDate", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")

	public Map<String,Object> GetListPlanningByUserAndDate(@RequestBody PlanningUserDto planningUser)
	{
		
		Map<String, Object> map = new HashMap<>(); 
		User u = userdao.findUserByIdUser(planningUser.getIdUser());
		Date nextDay = new Date();
		nextDay.setTime((planningUser.getDate().getTime()+86400000));
		
		List<Planning> plannings=planningDao.findPlanningByUserAndDate(u, planningUser.getDate(), nextDay );
		List<Recette> lstRecette=  new ArrayList<>();
		
		for(Planning p : plannings)
		{
			Recette recette=recettedao.findByIdRecette(p.getRecette().getIdRecette());
			recette.setUser(null);
			lstRecette.add(recette);
			
		}
		
		map.put("listPlanningUser", lstRecette);
		
		return map;
		
		
		
		
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/GetListPlanningByUserMonthYear", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String,Object> GetListPlanningByUserMonthYear(@RequestBody PlanningUserDto planningUser)
	{
		
		Map<String, Object> map = new HashMap<>(); 
		User u = userdao.findUserByIdUser(planningUser.getIdUser());
		
		SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
		
		String strYear=dateFormatYear.format(planningUser.getDate()); 
		String strMonth= dateFormatMonth.format(planningUser.getDate());
	
		int year=Integer.parseInt(strYear);
		
		int month=Integer.parseInt(strMonth);
		
		List<Planning> plannings=planningDao.findPlanningByMouthYears(u, month,year );
		List<Recette> lstRecette=  new ArrayList<>();
		Map<Date,List<Recette>> MapPlanningRecetteDto = new LinkedHashMap<>();
		
		for(Planning unPlanning : plannings)
		{
			List<Recette> lstRecetteValue=null;
			Recette recette=null;
			if(MapPlanningRecetteDto.size()>0)
			{
				if(MapPlanningRecetteDto.containsKey(unPlanning.getDatePlanning()))
				{
				
					
					
					recette=recettedao.findByIdRecette(unPlanning.getRecette().getIdRecette());
					recette.setUser(null);
					lstRecetteValue=MapPlanningRecetteDto.get(unPlanning.getDatePlanning());
					lstRecetteValue.add(recette);
					MapPlanningRecetteDto.put(unPlanning.getDatePlanning(), lstRecetteValue);
					
				}
				else 
				{
					lstRecetteValue=  new ArrayList<>();
					recette=recettedao.findByIdRecette(unPlanning.getRecette().getIdRecette());
					recette.setUser(null);
					lstRecetteValue.add(recette);
					MapPlanningRecetteDto.put(unPlanning.getDatePlanning(), lstRecetteValue);
					
					
				}
			}
			else {
				
				
				lstRecetteValue=  new ArrayList<>();
				recette=recettedao.findByIdRecette(unPlanning.getRecette().getIdRecette());
				recette.setUser(null);
				lstRecetteValue.add(recette);
				MapPlanningRecetteDto.put(unPlanning.getDatePlanning(), lstRecetteValue);
				
			}
				
				
			
			
		}
		
		map.put("listPlanningUser", MapPlanningRecetteDto);
		
		return map;
		
		
		
		
	}

}