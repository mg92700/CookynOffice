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
public class Unite{
	
	@Id
	@GeneratedValue
	private Integer idUnite;
	@Column
	private String libelleUnite;
	
	public Unite(Integer idUnite, String libelleUnite) {
		super();
		this.idUnite = idUnite;
		this.libelleUnite = libelleUnite;
	}
	public Unite() {
		super();
	}
	
	
	public Integer getIdUnite() {
		return idUnite;
	}
	public void setIdUnite(Integer idUnite) {
		this.idUnite = idUnite;
	}
	public String getLibelleUnite() {
		return libelleUnite;
	}
	public void setLibelleUnite(String libelleUnite) {
		this.libelleUnite = libelleUnite;
	}
	
}