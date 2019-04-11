package com.general.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.FavorisDao;
import com.general.dao.RecetteDao;
import com.general.dao.RelationDao;
import com.general.dao.UserDao;
import com.general.dto.UserDto;
import com.general.email.EmailServiceImpl;
import com.general.model.User;
import com.general.security.TokenSecurity;
import com.general.service.ApiService;
import com.general.service.Configuration;
import com.general.service.CryptageService;
import com.general.service.EmailValidator;


@Controller
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
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
	TokenSecurity t;
    
	
	EmailValidator validator = new EmailValidator();

	
	

	@RequestMapping(value = "/GetlistUsersByOffSet/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object> GetlistUsersByOffSet(@PathVariable int offset)
	{
		//List<User> users = userDao.findAll();
		List<User> users = userDao.findAllByCompteActive(1);
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
	
	@RequestMapping(value = "/GetUsersByUserName", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public UserDto GetUsersByUserName(String username)
	{
		User user = userDao.findByUsernameUser(username);
		UserDto userReturn = null;
		if(user!= null) {
			userReturn =(UserDto)JTransfo.convert(user);
			userReturn.setPasswordUser(null);
			userReturn.setNbRecetteCreate(recetteDao.findAllByUser(user).size());
			userReturn.setNbRecetteFav(favorisDao.findAllByUser(user).size());
			userReturn.setNbAbonnement(relationDao.findAllByUser(user).size());
			userReturn.setNbAbonnee(relationDao.findAllByFriend(user).size());
		}
		return userReturn;
	}
	
	@RequestMapping(value = "/GetUserById/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public UserDto GetUserById(@PathVariable int idUser)
	{
		User user = userDao.findUserByIdUserAndCompteActive(idUser,1);
		String format = "dd/MM/yy H:mm:ss"; 
		SimpleDateFormat formater = new SimpleDateFormat( format ); 
 

		UserDto userReturn = new UserDto();
		if(user!= null) {
			userReturn =(UserDto)JTransfo.convert(user);
			userReturn.setPasswordUser(null);
			userReturn.setNbRecetteCreate(recetteDao.findAllByUser(user).size());
			userReturn.setNbRecetteFav(favorisDao.findAllByUser(user).size());
			userReturn.setNbAbonnement(relationDao.findAllByUser(user).size() );
			userReturn.setNbAbonnee(   relationDao.findAllByFriend(user).size());
			
		}else {
		userReturn.setErrortxt("User est inconnu");}
		return userReturn;
	}

	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public UserDto CreateUser(@RequestBody UserDto userDto)
	{
		UserDto userReturn = new UserDto();
		
	User user =  null;
		if(userDto!=null)
		{
			System.out.println(userDao.findAllByUsernameUser(userDto.getUsernameUser()).size());
			if(userDao.findAllByUsernameUser(userDto.getUsernameUser()).size()==0 && userDao.findAllWhereMail(userDto.getMailUser()).size()==0){
			
				if(userDto.getPasswordUser()!= null) {
					userDto.setPasswordUser(cryptageService.encrypt(userDto.getPasswordUser()));	
					userDto.setRole("user");
					
					//String format = "dd/MM/yy H:mm:ss"; 
					//SimpleDateFormat formater = new SimpleDateFormat( format ); 
					//user.setDateCreation(formater.format( new Date()));
					userDto.setDateCreation(new Date());
					userDto.setDateModification(new Date());
					userDto.setDateDerniereConnection(new Date());
					userDto.setCompteActive(0);
					userDto.setMailVerifier(0);
					user = (User) JTransfo.convert(userDto);
					
					userReturn =(UserDto)JTransfo.convert(userDao.saveAndFlush(user));
					userReturn.setPasswordUser(null);
					userReturn.setNbRecetteCreate(recetteDao.findAllByUser(user).size());
					userReturn.setNbRecetteFav(favorisDao.findAllByUser(user).size());
					userReturn.setNbAbonnement(relationDao.findAllByFriend(user).size());
					userReturn.setNbAbonnee(relationDao.findAllByUser(user).size());
					

					EmailServiceImpl mailService= new EmailServiceImpl();
					try {
						mailService.sendSimpleMessageVerifMail(userDto.getMailUser(), " Verif Mail", user);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					userReturn.setErrortxt("Veuillez renseigner un mot de passe");
				}
			} else {
				userReturn.setErrortxt("Le username ou/et l'email est déjà utilisé");
			}
					
		}
		return userReturn;
	}
	
	@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public UserDto UpdateUser(@RequestBody UserDto userDto)
	{
		UserDto userReturn = null;
		User user = null;
		User userDb = userDao.findUserByIdUserAndCompteActive(userDto.getIdUser(),1);
		
		
		if(userDto!=null)
		{
			
			if(userDto.getNewPassword() != null) 
			{
				if(cryptageService.encrypt(userDto.getPasswordUser()).equals(userDao.findUserByIdUserAndCompteActive(userDto.getIdUser(),1).getPasswordUser())) 				
				{
					userDto.setPasswordUser(cryptageService.encrypt(userDto.getNewPassword()));
					user = (User) JTransfo.convert(userDto);
				}
				else {
					return userReturn;
				}
		
			}

			else {

				//u=userDao.findUserByIdUser(user.getIdUser());
				userDto.setPasswordUser(userDao.findUserByIdUserAndCompteActive(userDto.getIdUser(),1).getPasswordUser());
				user = (User) JTransfo.convert(userDto);
				
			}
			//prenom
			if(userDto.getPrenomUser()==null){
				user.setPrenomUser(userDb.getPrenomUser());
			}
			else 
			{
				user.setPrenomUser(userDto.getPrenomUser());
			}
			//nom
			if(userDto.getNomUser()==null){
				user.setNomUser(userDb.getNomUser());
			}
			else 
			{
				user.setNomUser(userDto.getNomUser());
			}
			//ville
			if(userDto.getVilleUser()==null){
				user.setVilleUser(userDb.getVilleUser());
			}
			else 
			{
				user.setVilleUser(userDto.getVilleUser());
			}
			//mail
			if(userDto.getMailUser()==null){
				user.setMailUser(userDb.getMailUser());
			}
			else 
			{
				user.setMailUser(userDto.getMailUser());
			}
			//username
			if(userDto.getUsernameUser()==null){
				user.setUsernameUser(userDb.getUsernameUser());
			}
			else 
			{
				user.setUsernameUser(userDto.getUsernameUser());
			}
			
			user.setDateCreation(userDb.getDateCreation());
			
		}
		if(user!= null) {
			user.setDateModification(new Date());
			userReturn =(UserDto)JTransfo.convert(userDao.saveAndFlush(user));
			userReturn.setPasswordUser(null);
			userReturn.setNbRecetteCreate(recetteDao.findAllByUser(user).size());
			userReturn.setNbRecetteFav(favorisDao.findAllByUser(user).size());
			userReturn.setNbAbonnement(relationDao.findAllByFriend(user).size()); 
			userReturn.setNbAbonnee(relationDao.findAllByUser(user).size());
			
		}
		return userReturn;
	}
	
	@RequestMapping(value = "/Login", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public UserDto Login(@RequestBody UserDto userDto)
	{
		User userDb = null;
		UserDto userReturn = new UserDto();
		User user=null;
		if(validator.validateEmail(userDto.getUsernameUser()))
		{
			//si c'est un email
			 if (userDao.findByMailUser(userDto.getUsernameUser()) != null ) {
				 userDb = userDao.findByMailUser(userDto.getUsernameUser());
			 	}
		
			
			
		}else {
			//si c'est pas un email
			if( userDao.findByUsernameUser(userDto.getUsernameUser()) != null ){
						userDb = userDao.findByUsernameUser(userDto.getUsernameUser());
				}
		}
		
	
		
		if(userDb!= null) 
		{
			if(userDb.getPasswordUser().equals(cryptageService.encrypt(userDto.getPasswordUser()))) 
			{
				userReturn =(UserDto)JTransfo.convert(userDao.saveAndFlush(userDb));
				userReturn.setPasswordUser(null);
				userReturn.setNbRecetteCreate(recetteDao.findAllByUser(userDb).size());
				userReturn.setNbRecetteFav(favorisDao.findAllByUser(userDb).size());
				userReturn.setNbAbonnement(relationDao.findAllByFriend(userDb).size());
				userReturn.setNbAbonnee(relationDao.findAllByUser(userDb).size());
				
				user=userDb;
				user.setDateCreation(new Date());
				user.setCompteActive(1);
				userDao.saveAndFlush(user);
				
				
			}
			else {
				
				userReturn.setErrortxt("Combinaison e-mail & mot de passe incorrect");
			}
		}
		else 
		{
			userReturn.setErrortxt("User est inconnue");
		}
		return userReturn;
	}

	@RequestMapping(value = "/GetListUserByFiltre/{filtre}/{offset}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Map<String, Object>  GetListUserByFiltre(@PathVariable String filtre,@PathVariable int offset)
	{
		
		List<User> users = userDao.findAllByFiltre(filtre);
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

	@RequestMapping(value = "/DeconnexionUser/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Boolean DeconnexionUser(@PathVariable int idUser)
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
	
	@RequestMapping(value = "/VerifUserMail/{idUser}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public String VerifUserMail(@PathVariable int idUser)
	{
		User userDb = userDao.findUserByIdUser(idUser);
		User user =null;
		if(userDb!=null)
		{
			if(userDb.getMailVerifier()!=1) {
				user=userDb;
				user.setMailVerifier(1);
				userDao.saveAndFlush(user);
				return "Confirmé";	
			}
			else {
				return "Déjà Confirmé";	
			}
		}
		return "Erreur";
		
	}
	
	@RequestMapping(value = "/ForgotPassword", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public String ForgotPassword(@RequestBody UserDto userDto)
	{
		User userDb = userDao.findWhereMail(userDto.getMailUser());
		User user =null;
		Configuration config = new Configuration();
		if(userDb!=null)
		{
			if(userDb.getMailVerifier()==1)
			{
				user=userDb;
				String NewPassWord=config.generate();
				user.setPasswordUser(cryptageService.encrypt(NewPassWord));
				
				userDao.saveAndFlush(user);
				EmailServiceImpl e= new EmailServiceImpl();
				try {
					e.sendSimpleMessagePassWordOublier(user.getMailUser(), " Mot de passe provisoire", user, NewPassWord);
				} catch (AddressException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return "Une erreur s'est produite lors de l'envoie de l'email";	
					
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return "Une erreur s'est produite lors de l'envoie de l'email";	
					
				}
				return "Email envoyé";	
	
			}
			else {
				return "L'email n'est pas vérifié";	
			}
		}
		return "Utilisateur introuvable";
		
	}
	
	
	

}