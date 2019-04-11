package com.general.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.general.dao.FavorisDao;
import com.general.dao.RecetteDao;
import com.general.dao.StatistiqueDao;
import com.general.dao.UserDao;
import com.general.model.Favoris;
import com.general.model.Recette;
import com.general.model.Statistique;
import com.general.model.User;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    

	@Autowired
	UserDao userDao;

    @Autowired
    RecetteDao recetteDao;
    

    @Autowired
    StatistiqueDao statistiqueDao;
    
    @Autowired
    FavorisDao favorisDao;
    
    /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
    String d = dateFormat.format(date).toString()*/
	Date date = new Date();
    

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        log.info("Début traitement le {}", dateFormat.format(new Date()));


        NombreUser();
        NombreUserConnecter();
        moyenneRecettesByUser();
        NombreUserAyantEmailPasVerfier();
        tempsMoyenMinutesUtilisateur();
        NombreRecette();
        RecetteLaPlusFavorite();
        try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.info("Fin traitement le {}", dateFormat.format(new Date()));
        
    }
    
		private void moyenneRecettesByUser() {
		    	
		    	
			Statistique s =statistiqueDao.findByLibelle("Moyenne de recette par utilisateur");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
				//On récupère d'abord le nombre total de recettes
		      
			    	List<Recette> recettes = recetteDao.findAll();
			        int nbRecettes = recettes.size();
			
					//Puis on récupère le nombre total d'utilisateurs
			        List<User> users = userDao.findAll();
			        int nbUsers = users.size();
			
					//On divise ensuite le nombre de recettes par le nombre d'utilisateurs
			        float  moyen= (float) nbRecettes / nbUsers;
			        value=String.valueOf(moyen);
		      
		        
		        uneStatisque.setLibelleStatistique("Moyenne de recette par utilisateur");
		        uneStatisque.setDateStatistique(date);
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				
				String value=null;
				//On récupère d'abord le nombre total de recettes
		      
			    	List<Recette> recettes = recetteDao.findAll();
			        int nbRecettes = recettes.size();
			
					//Puis on récupère le nombre total d'utilisateurs
			        List<User> users = userDao.findAll();
			        int nbUsers = users.size();
			
					//On divise ensuite le nombre de recettes par le nombre d'utilisateurs
			        float  moyen= (float) nbRecettes / nbUsers;
			        value=String.valueOf(moyen);
			        s.setDateStatistique(date = new Date());
			    
			        s.setValeurStatistique(value);
			        statistiqueDao.save(s);
			}
			
		}

		private void NombreUser() {
		    	
		    	
			Statistique s =statistiqueDao.findByLibelle("Nombre d'utilisateur");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
			
			
					//Puis on récupère le nombre total d'utilisateurs
			        List<User> users = userDao.findAll();
			        int nbUsers = users.size();
		
			        value=String.valueOf(nbUsers);
		      
		        
		        uneStatisque.setLibelleStatistique("Nombre d'utilisateur");
		        uneStatisque.setDateStatistique(date);
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				
			 	String value=null;
				
				
				//Puis on récupère le nombre total d'utilisateurs
		        List<User> users = userDao.findAll();
		        int nbUsers = users.size();
	
		        value=String.valueOf(nbUsers);
			        s.setDateStatistique(date = new Date());
			        s.setValeurStatistique(value);
			        statistiqueDao.save(s);
			}
			
		}
		
		private void NombreUserConnecter()
		{
			Statistique s =statistiqueDao.findByLibelle("Nombre d'utilisateur connecter");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
			
			
					//Puis on récupère le nombre total d'utilisateurs
			        List<User> users = userDao.findAllUserConnecte();
			        int nbUsers = users.size();
		
			        value=String.valueOf(nbUsers);
		      
		        
		        uneStatisque.setLibelleStatistique("Nombre d'utilisateur connecter");
		        uneStatisque.setDateStatistique(date = new Date());
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				
			 	String value=null;
				
				
				//Puis on récupère le nombre total d'utilisateurs
		        List<User> users = userDao.findAllUserConnecte();
		        int nbUsers = users.size();
	
		        value=String.valueOf(nbUsers);
			        s.setDateStatistique(date);
			        s.setValeurStatistique(value);
			        statistiqueDao.save(s);
			}
			
		}

		private void NombreUserAyantEmailPasVerfier()
		{
			Statistique s =statistiqueDao.findByLibelle("Nombre d'utilisateur avec le mail pas vérifier");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
			
			
					//Puis on récupère le nombre total d'utilisateurs
			        List<User> users = userDao.findAllUserMailVerif();
			        int nbUsers = users.size();
		
			        value=String.valueOf(nbUsers);
		      
		        
		        uneStatisque.setLibelleStatistique("Nombre d'utilisateur avec le mail pas vérifier");
		        uneStatisque.setDateStatistique(date = new Date());
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				
			 	String value=null;
				
				
				//Puis on récupère le nombre total d'utilisateurs
		        List<User> users = userDao.findAllUserMailVerif();
		        int nbUsers = users.size();
	
		        value=String.valueOf(nbUsers);
			        s.setDateStatistique(date);
			        s.setValeurStatistique(value);
			        statistiqueDao.save(s);
			}
			
		}
		
		private void tempsMoyenMinutesUtilisateur()
		{
			Statistique s =statistiqueDao.findByLibelle("Temps d'utilisation de l'application en moyenne");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
			
			
					//Puis on récupère le nombre total d'utilisateurs
		    		List<Long> listTemps= new ArrayList<Long>();
			        List<User> users = userDao.findAllUserRole();
			        
			        for(User u: users)
			        {
			        	Date d= new Date();
			        	long diff =d.getTime()-u.getDateDerniereConnection().getTime();
			        	long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
			        	listTemps.add(minutes);
			        	
			        }
			        long moyen = 0;
		
			        for(Long l:listTemps)
			        {
			        	
			        	moyen+=l;
			        }
			        
			        value=String.valueOf(moyen);
		      
		        
		        uneStatisque.setLibelleStatistique("Temps d'utilisation de l'application en moyenne");
		        uneStatisque.setDateStatistique(date = new Date());
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				String value=null;
				//Puis on récupère le nombre total d'utilisateurs
	    		List<Long> listTemps= new ArrayList<Long>();
		        List<User> users = userDao.findAllUserRole();
		        
		        for(User u: users)
		        {
		        	Date d= new Date();
		        	long diff =d.getTime()-u.getDateDerniereConnection().getTime();
		        	long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		        	listTemps.add(minutes);
		        	
		        }
		        long moyen = 0;
	
		        for(Long l:listTemps)
		        {
		        	
		        	moyen+=l;
		        }
		        
		     
	
		        value=String.valueOf(moyen);
			        s.setDateStatistique(date);
			        s.setValeurStatistique(value);
			        statistiqueDao.save(s);
			}
			
		}
		
		private void NombreRecette() {
	    	
	    	
			Statistique s =statistiqueDao.findByLibelle("Nombre de recette");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
			
			
					//Puis on récupère le nombre total d'utilisateurs
			        List<Recette> recettes = recetteDao.findAll();
			        int nbRecette = recettes.size();
		
			        value=String.valueOf(nbRecette);
		      
		        
		        uneStatisque.setLibelleStatistique("Nombre de recette");
		        uneStatisque.setDateStatistique(date);
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				
			 	String value=null;
				
				
				//Puis on récupère le nombre total d'utilisateurs
			 	List<Recette> recettes = recetteDao.findAll();
		        int nbRecette = recettes.size();
	
		        value=String.valueOf(nbRecette);
			        s.setDateStatistique(date = new Date());
			        s.setValeurStatistique(value);
			        statistiqueDao.save(s);
			}
			
		}
		
		

		private void RecetteLaPlusFavorite() {
	    	
	    	
			Statistique s =statistiqueDao.findByLibelle("Recette du moment");
			
			if(s==null)
			{
		    	Statistique uneStatisque = new Statistique();
		    	String value=null;
			
		    		Recette recetteMax=null;
					//Puis on récupère le nombre total d'utilisateurs
			        List<Recette> recettes = recetteDao.findAll();
			        int nombreElement=0;
			        int nombreMax=0;
			        for(Recette recette:recettes)
			        {
			        	List<Favoris> favoris = favorisDao.findAllByRecette(recette);
			        	nombreElement=favoris.size();
			        	if(nombreElement > nombreMax)
			        	{
			        		recetteMax=null;
			        		nombreElement=nombreMax;
			        		recetteMax=recette;
			        		
			        	}
			        
			        }
			       
			        if(recetteMax==null) {
			        	value=String.valueOf("Aucune recette pour le moment");
			        }else {
			        value=String.valueOf(recetteMax.getLibelleRecette());
			        }
		        
		        uneStatisque.setLibelleStatistique("Recette du moment");
		        uneStatisque.setDateStatistique(date);
		        uneStatisque.setValeurStatistique(value);
		        statistiqueDao.save(uneStatisque);
			}
			else {
				
				
				String value=null;
				
	    		Recette recetteMax=null;
				//Puis on récupère le nombre total d'utilisateurs
		        List<Recette> recettes = recetteDao.findAll();
		        int nombreElement=0;
		        int nombreMax=0;
		        for(Recette recette:recettes)
		        {
		        	List<Favoris> favoris = favorisDao.findAllByRecette(recette);
		        	nombreElement=favoris.size();
		        	if(nombreElement > nombreMax)
		        	{
		        		recetteMax=null;
		        		nombreElement=nombreMax;
		        		recetteMax=recette;
		        		
		        	}
		        
		        }
		       
		        if(recetteMax==null) {
		        	value=String.valueOf("Aucune recette pour le moment");
		        }else {
		        value=String.valueOf(recetteMax.getLibelleRecette());
		        }
	        
	        s.setLibelleStatistique("Recette du moment");
	        s.setDateStatistique(date);
	        s.setValeurStatistique(value);
	        statistiqueDao.save(s);
			}
			
		}
		

}