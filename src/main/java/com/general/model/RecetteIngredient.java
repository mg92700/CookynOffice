package com.general.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RecetteIngredient implements Serializable {
	
	@Id
	@GeneratedValue
	private int idRecetteIngredient;
	
	@ManyToOne
	@JoinColumn(name="idRecette")
	private Recette recette;
	@ManyToOne
	@JoinColumn(name="idIngredient")
	private Ingredient ingredient;
	@ManyToOne
	@JoinColumn(name="idUnite")
	private Unite unite;
	@Column
	private float quantite;
	
	
	
	public RecetteIngredient(int idRecetteIngredient,Recette recette, Ingredient ingredient, Unite unite, float quantite) {
		super();
		this.idRecetteIngredient=idRecetteIngredient;
		this.recette = recette;
		this.ingredient = ingredient;
		this.unite = unite;
		this.quantite = quantite;
	}


	public RecetteIngredient() {
		super();
	}
	
	
	
	public int getIdRecetteIngredient() {
		return idRecetteIngredient;
	}


	public void setIdRecetteIngredient(int idRecetteIngredient) {
		this.idRecetteIngredient = idRecetteIngredient;
	}


	public Recette getRecette() {
		return recette;
	}
	public void setRecette(Recette recette) {
		this.recette = recette;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	public Unite getUnite() {
		return unite;
	}
	public void setUnite(Unite unite) {
		this.unite = unite;
	}
	public float getQuantite() {
		return quantite;
	}
	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}
	
	
}


