package com.general.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.NotMapped;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IngredientCourse {
	
	@NotMapped 
	String libelleIngredient;

	
	@NotMapped 
	int idIngredient;


	
	
	@NotMapped 
	String categorie;
	
	@NotMapped 
	List<RelationUniteQuantiteDto> lstRelationUniteDto;

	public String getLibelleIngredient() {
		return libelleIngredient;
	}

	public void setLibelleIngredient(String libelleIngredient) {
		this.libelleIngredient = libelleIngredient;
	}

	public int getIdIngredient() {
		return idIngredient;
	}

	public void setIdIngredient(int idIngredient) {
		this.idIngredient = idIngredient;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public List<RelationUniteQuantiteDto> getLstRelationUniteDto() {
		return lstRelationUniteDto;
	}

	public void setLstRelationUniteDto(List<RelationUniteQuantiteDto> lstRelationUniteDto) {
		this.lstRelationUniteDto = lstRelationUniteDto;
	}

	public IngredientCourse(String libelleIngredient, int idIngredient, String categorie,
			List<RelationUniteQuantiteDto> lstRelationUniteDto) {
		super();
		this.libelleIngredient = libelleIngredient;
		this.idIngredient = idIngredient;
		this.categorie = categorie;
		this.lstRelationUniteDto = lstRelationUniteDto;
	}

	public IngredientCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}