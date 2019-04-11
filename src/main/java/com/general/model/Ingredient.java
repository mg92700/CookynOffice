package com.general.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ingredient{
	
	@Id
	@GeneratedValue
	private Integer idIngredient;
	@Column(nullable = false)
	private String catIngredient;
	@Column(nullable = false)
	private String libelleIngredient;
	public Ingredient(Integer idIngredient, String catIngredient, String libelleIngredient) {
		super();
		this.idIngredient = idIngredient;
		this.catIngredient = catIngredient;
		this.libelleIngredient = libelleIngredient;
	}
	public Ingredient() {
		super();
	}
	public Integer getIdIngredient() {
		return idIngredient;
	}

	public void setIdIngredient(Integer idIngredient) {
		this.idIngredient = idIngredient;
	}

	public String getCatIngredient() {
		return catIngredient;
	}
	public void setCatIngredient(String catIngredient) {
		this.catIngredient = catIngredient;
	}
	public String getLibelleIngredient() {
		return libelleIngredient;
	}
	public void setLibelleIngredient(String libelleIngredient) {
		this.libelleIngredient = libelleIngredient;
	}
	
	
}