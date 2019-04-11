package com.general.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.NotMapped;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseParamDto {

	
	@NotMapped 
	private Date dateDebut;
	
	
	@NotMapped 
	private Date dateFin;
	
	
	@NotMapped 
	private Integer idUser;


	public Date getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Date getDateFin() {
		return dateFin;
	}


	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	public Integer getIdUser() {
		return idUser;
	}


	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}


	public CourseParamDto(Date dateDebut, Date dateFin, Integer idUser) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.idUser = idUser;
	}


	public CourseParamDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
