package com.general.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Etape{
	
	@Id
	@GeneratedValue
	private int idEtape;
	@Column
	private String descriptionEtape;
	@Column
	private int indexEtape;
	@ManyToOne
	@JoinColumn(name="idRecette")
	private Recette recette;
	
	public Etape() {
		super();
	}

	public Etape(int idEtape, String descriptionEtape, int indexEtape, Recette recette) {
		super();
		this.idEtape = idEtape;
		this.descriptionEtape = descriptionEtape;
		this.indexEtape = indexEtape;
		this.recette = recette;
	}

	public int getIndexEtape() {
		return indexEtape;
	}

	public void setIndexEtape(int indexEtape) {
		this.indexEtape = indexEtape;
	}

	public int getIdEtape() {
		return idEtape;
	}
	public void setIdEtape(int idEtape) {
		this.idEtape = idEtape;
	}
	public String getDescriptionEtape() {
		return descriptionEtape;
	}
	public void setDescriptionEtape(String descriptionEtape) {
		this.descriptionEtape = descriptionEtape;
	}
	public Recette getRecette() {
		return recette;
	}
	public void setRecette(Recette recette) {
		this.recette = recette;
	}	
}