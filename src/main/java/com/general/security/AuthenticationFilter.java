package com.general.security;
import org.apache.commons.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


@WebFilter("/admin/*")
public class AuthenticationFilter implements Filter{

	  
	 private TokenSecurity t = new TokenSecurity();

	 @Override
	 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	      throws IOException, ServletException {

	    HttpServletRequest request = (HttpServletRequest) servletRequest;
	    HttpServletResponse response = (HttpServletResponse) servletResponse;

	    
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        
	    //recupere la routes dans la reponse

        String path = ((HttpServletRequest) servletRequest).getServletPath();
      System.out.println(map);
        
		if(this.GetListAutoriseRoutes().contains(path))
        {
        	filterChain.doFilter(servletRequest, servletResponse);

        }
        //recupere value depuis la clés
        String Token=map.get("authorization");
	    if (Token == null) 
	    {
	    	
	    	
	    	if("/admin/LogAdmin".equals(path))
		    {
		  	  filterChain.doFilter(servletRequest, servletResponse);
		    }
	    	else
	    //  StringTokenizer st = new StringTokenizer(Token);
	    	{
			 response.setStatus(HttpServletResponse.SC_FORBIDDEN,"Incorrecte");
	    	}
	    }
	    	
	    else {

		 
		 if (t.VerifTokenActif(Token)) 
         {
       	  filterChain.doFilter(servletRequest, servletResponse);
       		
 
              //verifier s'il peut ou pas sans connection

         }

        else {
       	 response.setStatus(HttpServletResponse.SC_FORBIDDEN,"Incorrecte");
       	
       
       }
	      
	    }
	  }
	
	 @Override
	 public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	 @Override
	 public void destroy() {
		// TODO Auto-generated method stub
		
	}
	 
	 private void unauthorized(HttpServletResponse response) throws IOException {
		    unauthorized(response);
		  }
	

	    public List<String> GetListAutoriseRoutes()

	    {

	          //Liste des routes accessible

	          List<String> lesRoutesAutorise= new  ArrayList<String>();

	          lesRoutesAutorise.add("/admin/LogAdmin");

	          lesRoutesAutorise.add("");

	          lesRoutesAutorise.add("");

	          lesRoutesAutorise.add("");

	          lesRoutesAutorise.add("");

	          lesRoutesAutorise.add("");

	          lesRoutesAutorise.add("");

	          return lesRoutesAutorise;

	         

	      

	         

	    }

}
