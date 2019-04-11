package com.general.dto;

import java.util.Date;

import org.jtransfo.MappedBy;
import org.jtransfo.NotMapped;

public class PlanningUserDto {
	
	@NotMapped
	private Integer idUser;

	
	@NotMapped 
	private Date date;


	public Integer getIdUser() {
		return idUser;
	}


	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public PlanningUserDto(Integer idUser, Date date) {
		super();
		this.idUser = idUser;
		this.date = date;
	}


	public PlanningUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
