package com.general.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.general.dao.UniteDao;
import com.general.model.Recette;
import com.general.model.Unite;
import com.general.service.ApiService;
import com.general.service.CryptageService;


@Controller
@RestController
@RequestMapping(value = "/unite")

public class UniteController {
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	JTransfo JTransfo;
	
	@Autowired
	UniteDao uniteDao;
	
	@Autowired 
	CryptageService cryptageService;
	
	
	@RequestMapping(value = "/GetListUnites", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public List<Unite>  GetListUnites()
	{
		List<Unite> unite = uniteDao.findAll();
		
		return unite;
	}
	
	@RequestMapping(value = "/UniteByLibelle/{libelleUnite}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Unite UniteByLibelle(@PathVariable String libelleUnite)
	{
		Unite unite = uniteDao.findBylibelleUnite(libelleUnite);
		return unite;
	}
	
	@RequestMapping(value = "/UniteById/{idUnite}", method = RequestMethod.GET,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Unite UniteById(@PathVariable int idUnite)
	{
		Unite unite = uniteDao.findByidUnite(idUnite);
		return unite;
	}
	
	@RequestMapping(value = "/AddUnite", method = RequestMethod.POST,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Unite AddUnite(@RequestBody Unite uneUniteParam)
	{
		
		if(uneUniteParam!=null)
		{
			Unite uneUnite = uniteDao.saveAndFlush(uneUniteParam);
			return uneUnite;
		}
		else
			return null;
	}
	
	@RequestMapping(value = "/UpdateUnite", method = RequestMethod.PUT,headers="Accept=application/json")
	@CrossOrigin(origins = "*")
	public Unite UpdateUnite(@RequestBody Unite uni)
	{
		uni=uniteDao.saveAndFlush(uni);
		return uni;
	}
	
	
}