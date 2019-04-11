package com.general.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.general.service.CryptageService;
@Component
public class TokenSecurity {
	
	private static final long TICKS_AT_EPOCH = 621355968000000000L;
    private static final long TICKS_PER_MILLISECOND = 10000;
    private static final long trenteMinutes=18000000000L;
    private  CryptageService crypt = new CryptageService();
    
    
    public static long getUTCTicks(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return (calendar.getTimeInMillis() * TICKS_PER_MILLISECOND) + TICKS_AT_EPOCH;

    }

    public static Date getDate(long UTCTicks){

        return new Date((UTCTicks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND);

    }
    
    public Boolean VerifTokenActif(String strToken)

    {
    	strToken=strToken.replace("Bearer ", "");
    	Date date = java.util.Calendar.getInstance().getTime();
    	long tickNow = getUTCTicks(date); //déclare la primitive time1 à partir de date1
       
        String tic = null;
        Boolean bonneValeur = false;
        try 
        {
        	 String s = crypt.decrypt(strToken);
        	 if(s!=null)
        	 {
		        for (int i = 0; i < s.length();i++ )
		        {
		
		            char c = s.charAt(i);
		
		            if (c == '_'){
		
		                bonneValeur = true;
		
		            }
		
		            if (s.charAt(i) == '|'){
		
		                break;
		
		            }
		
		            if (bonneValeur == true)
		            {
		
		                tic += s.charAt(i);
		
		
		            }
		        }

		        String ticReplace = tic.replace("_","");
		        String ticReplaceOffice=ticReplace.replaceAll("null", "");
		        long tokenTime = Long.parseLong(ticReplaceOffice);
		
		        Integer value =  (int) (tickNow-tokenTime);
		
		        if (value < trenteMinutes)
		
		        {
		        	
		
		            return true;
		
		        }
        
        	 }
        }catch(Exception e)
        {
        	return false;
        }

        return false;

    }

    public String getToken()
    {
    	Date date = java.util.Calendar.getInstance().getTime();

        long ms = getUTCTicks(date);

        String token = "006dea24d8a601a5389977bacb5bef95" + "0465ecdb0c2011e8cf2887499cca346c5ebf047e5a936a9188411acfc2e005b4c9fd316cb2d20cb808d3477793cef631fe47055d8a93418091f8595a7abc119d" + "IciC'estParis/" + "email@tonton.fr" + "_" + ms + "|006dea24d8a601a5389977bacb5bef95";           
        String tokenEncrypt=crypt.encrypt(token);

        return tokenEncrypt;


    }

}
