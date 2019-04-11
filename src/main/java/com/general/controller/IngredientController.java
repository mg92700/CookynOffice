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

import com.general.dao.IngredientDao;
import com.general.model.Ingredient;
import com.general.model.Note;
import com.general.model.Recette;
import com.general.service.ApiService;
import com.general.service.CryptageService;

@Controller
@RestController
@RequestMapping(value = "/ingredient")

public class IngredientController {

    @Autowired
    ApiService apiService;
    
    @Autowired
    JTransfo JTransfo;
    
    @Autowired
    IngredientDao ingredientDao;
    
    @Autowired
    CryptageService cryptageService;
    
    @RequestMapping(value = "/GetlistIngredientLibelle/{name}", method = RequestMethod.GET,headers="Accept=application/json")
    @CrossOrigin(origins = "*")
    public List<Ingredient> GetlistIngredientLibelle(@PathVariable String name)
    {
        List<Ingredient> ingredients = ingredientDao.findAllWhereNom(name);
		return ingredients;
    }
    
    @RequestMapping(value = "/GetListAllIngredient", method = RequestMethod.GET,headers="Accept=application/json")
    @CrossOrigin(origins = "*")
    public List<Ingredient> GetListAllIngredient()
    {
    	
    	 List<Ingredient> ingredients = ingredientDao.findAll();
    	 return ingredients;
    }
    
    @RequestMapping(value = "/GetListCatIngredient/{name}", method = RequestMethod.GET,headers="Accept=application/json")
    @CrossOrigin(origins = "*")
    public List<Ingredient>  GetListCatIngredient(@PathVariable String name)
    {
        List<Ingredient> ingredients = ingredientDao.findAllWhereCat(name);
       
 		return ingredients;

    }
    
    @RequestMapping(value = "/AddIngredient", method = RequestMethod.POST,headers="Accept=application/json")
    @CrossOrigin(origins = "*")
    public Ingredient AddIngredient(@RequestBody Ingredient ig)
    {
        
        if(ig!=null)
        {
            Ingredient addig = ingredientDao.saveAndFlush(ig);
            return addig;
        }
        else
            return null;
    }
    
    @RequestMapping(value = "/UpdateIngredient", method = RequestMethod.PUT,headers="Accept=application/json")
    @CrossOrigin(origins = "*")
    public Ingredient UpdateIngredient(@RequestBody Ingredient ingredient)
    {
        ingredient=ingredientDao.saveAndFlush(ingredient);
        return ingredient;
    }
    
}