package com.general.controller;

import java.util.Date;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.general.dao.TokenUserDao;
import com.general.dao.UserDao;
import com.general.dto.TokenUserDto;
import com.general.model.TokenUser;
import com.general.service.ApiService;
import com.general.service.CryptageService;
import com.general.security.TokenSecurity;;

@Controller
@RestController
@RequestMapping(value = "/tokenuser")
public class TokenUserController {
	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	@Autowired
	TokenUserDao tokenUserDao;

	@Autowired
	UserDao userDao;
	
	@Autowired 
	CryptageService cryptageService;
	
	@Autowired
	TokenSecurity tokenSecurity;
	
	private static final long TICKS_AT_EPOCH = 621355968000000000L;
    private static final long trenteMinutes=18000000000L;

	
	private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static int charLength = chars.length();
    
    public static String chaineAleatoire() {
        StringBuilder  chaine = new StringBuilder (charLength);
        for (int x = 0; x < 8; x++) {
            int i = (int) (Math.random() * charLength);
            chaine.append(chars.charAt(i));
        }
        return chaine.toString();
    }
    
    
    @RequestMapping(value = "/CreateTokenUser", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public TokenUserDto CreateTokenUser(@RequestBody TokenUserDto tokenUserDto)
	{
    	String chaine = chaineAleatoire();
    	
		TokenUserDto tokenUserReturn = new TokenUserDto();
		TokenUser tokenUser =  null;
		if(tokenUserDto!=null)
		{					
			tokenUserDto.setToken(chaine);
			
			tokenUserDto.setDateCreation(new Date());
			tokenUserDto.setDateModification(new Date());
			tokenUser = (TokenUser) JTransfo.convert(tokenUserDto);	
			
			tokenUserReturn =(TokenUserDto)JTransfo.convert(tokenUserDao.saveAndFlush(tokenUser));

		}else {
			tokenUserReturn.setErrortxt("Veuillez renseigner un bon tokenUser");
		}
					
		return tokenUserReturn;
	}
    
    
    public Boolean Verif(int idTokenUser) {
    	TokenUser tokenUser = new TokenUser();
    	tokenUser.setIdTokenUser(idTokenUser);
    	Date date= tokenUser.getDateCreation();
    	
    	return (TICKS_AT_EPOCH-tokenSecurity.getUTCTicks(date)>trenteMinutes);
    }
    
}