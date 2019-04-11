package com.general.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.DomainClass;
import org.jtransfo.MappedBy;

import lombok.Data;

@Data
@DomainClass("com.general.model.Statistique")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StatistiqueDto {
	
	@MappedBy
	private int idStatistique;
	@MappedBy
	private String libelleStatistique;
	@MappedBy
	private String valeurStatistique;
	@MappedBy
	private Date dateStatistique;
	
	public int getIdStatistique() {
		return idStatistique;
	}
	public void setIdStatistique(int idStatistique) {
		this.idStatistique = idStatistique;
	}
	public String getLibelleStatistique() {
		return libelleStatistique;
	}
	public void setLibelleStatistique(String libelleStatistique) {
		this.libelleStatistique = libelleStatistique;
	}
	public String getValeurStatistique() {
		return valeurStatistique;
	}
	public void setValeurStatistique(String valeurStatistique) {
		this.valeurStatistique = valeurStatistique;
	}
	public Date getDateStatistique() {
		return dateStatistique;
	}
	public void setDateStatistique(Date dateStatistique) {
		this.dateStatistique = dateStatistique;
	}
	public StatistiqueDto(int idStatistique, String libelleStatistique, String valeurStatistique,
			Date dateStatistique) {
		super();
		this.idStatistique = idStatistique;
		this.libelleStatistique = libelleStatistique;
		this.valeurStatistique = valeurStatistique;
		this.dateStatistique = dateStatistique;
	}
	public StatistiqueDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
