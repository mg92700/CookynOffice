package com.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.general.dao.UniteDao;
import com.general.model.Unite;
@Transactional
@Service
public class ConvertUnite {
	@Autowired
	UniteDao uniteDao;

	
	public float Convert(int idUnite, float value) {
		Unite u = uniteDao.findByidUnite(idUnite);
		float valueUnite = (float) 0;
		if(u != null) {
			if(u.getLibelleUnite().equals("Grammes")) return value;
			else if(u.getLibelleUnite().equals("Kilogrammes")) return value * 1000;
			else if(u.getLibelleUnite().equals("Cuillère à café")) return (float) (value * 0.5);
			else if(u.getLibelleUnite().equals("Cuillère à soupe")) return (float) (value * 1.5);
			else if(u.getLibelleUnite().equals("Litres")) return value * 1000;
			else if(u.getLibelleUnite().equals("Centilitres")) return value * 10;
		}
		return valueUnite;
	}
	
	public float ConvertToCentilitres(int idUnite, float value) {
		Unite u = uniteDao.findByidUnite(idUnite);
		float valueUnite = (float) 0;
		if(u != null) {
			if(u.getLibelleUnite().equals("Cuillère à café")) return (float) (value * 0.5);
			else if(u.getLibelleUnite().equals("Cuillère à soupe")) return (float) (value * 1.5);
			else if(u.getLibelleUnite().equals("Litres")) return (float) (value * 100);
			else if (u.getLibelleUnite().equals("Centilitres")) return value;
		}
		return valueUnite;
	}
	
	public float ConvertToGrammes(int idUnite, float value) {
		Unite u = uniteDao.findByidUnite(idUnite);
		float valueUnite = (float) 0;
		if(u != null) {
			if(u.getLibelleUnite().equals("Grammes")) return value;
			else if(u.getLibelleUnite().equals("Kilogrammes")) return value * 1000;
		}
		return valueUnite;
	}
}
