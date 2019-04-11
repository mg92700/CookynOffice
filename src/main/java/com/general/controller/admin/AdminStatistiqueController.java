package com.general.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.general.model.Recette;
import com.general.model.Statistique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.UserDao;
import com.general.model.User;

import com.general.dao.RecetteDao;
import com.general.dao.StatistiqueDao;

@Controller
@RestController
@RequestMapping(value = "/adminStatistique")
public class AdminStatistiqueController {
	
	
	@Autowired
	UserDao userDao;

    @Autowired
    RecetteDao recetteDao;


    @Autowired
    StatistiqueDao statistiqueDao;
    
    
    
    
	
	
	///renvoie les users cree par date choisie par rapport à la date d'aujourd'hui
		@RequestMapping(value = "/GetStatistiqueUserByDate/{date}", method = RequestMethod.GET,headers="Accept=application/json")
		@CrossOrigin(origins = "*")
		public Map<String, Object> GetlistUsersByOffSet(@PathVariable String date)
		{
			
			Map<String, Object> map = new HashMap<>(); 
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date dateParam = null;
			try {
				 dateParam=sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Date dateNow = new Date();
			
			
			List<User> lstUser= userDao.findAllUserByDate(dateNow, dateParam);
			map.put("UserCountByDate", lstUser.size());
			
			return map;
			
			
		}
		
		///renvoie les users connecter
		@RequestMapping(value = "/GetStatistiqueUserByDate", method = RequestMethod.GET,headers="Accept=application/json")
		@CrossOrigin(origins = "*")
		public Map<String, Object> GetNombreUserConnecter()
		{
			
			Map<String, Object> map = new HashMap<>(); 
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date dateParam = null;
		
			
			
			List<User> lstUser= userDao.findAllUserConnecte();
			map.put("UserCountByDate", lstUser.size());
			
			return map;
			
			
		}

		
		@RequestMapping(value = "/GetlisteStatistique", method = RequestMethod.GET,headers="Accept=application/json")
		@CrossOrigin(origins = "*")
		public Map<String, Object> GetlistUsersByOffSet()
		{
			List<Statistique> Statistiques = statistiqueDao.findAll();
			
			Map<String, Object> map = new HashMap<>(); 
			
			map.put("listStatistique", Statistiques);
	
			return map;
			
			
		}
		

		@RequestMapping(value = "/GetSubUserByMonth", method = RequestMethod.GET,headers="Accept=application/json")
		@CrossOrigin(origins = "*")
		public List<String> GetSubUserByMonth()
		{
			Date endDate = new Date();
			Date startDate = null;
			Calendar c;
			//c.add(Calendar.MONTH, -5);
			//startDate = c.getTime();
			
			
			List<String> test=new ArrayList();
			for (int i=5;i>=0;i--) {
				c = Calendar.getInstance();
				c.setTime(endDate); 
				if(i!=0)
					c.add(Calendar.MONTH, -i);
				c.set(Calendar.DAY_OF_MONTH, 1);
				startDate = c.getTime();
				c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
				Date tempEndDate=c.getTime();
				
				System.out.println("startDate : "+startDate.toString());
				System.out.println("tempEndDate : "+tempEndDate.toString()+"\n");
				
				Integer Statistiques = statistiqueDao.countUserByMonth(startDate,tempEndDate);
				
				test.add(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth()+ "/"+startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()+","+Statistiques);
			}
			return test;
		}
}