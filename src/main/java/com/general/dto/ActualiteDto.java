package com.general.dto;

import java.util.Date;

import org.jtransfo.MappedBy;

import com.general.model.User;

public class ActualiteDto {
	
	@MappedBy
	private int idActualite;
	@MappedBy
	private String typeActualite;
	@MappedBy
	private User what;
	@MappedBy
	private Date date;
	@MappedBy
	private WhoDto whoDto;
	public int getIdActualite() {
		return idActualite;
	}
	public void setIdActualite(int idActualite) {
		this.idActualite = idActualite;
	}
	public String getTypeActualite() {
		return typeActualite;
	}
	public void setTypeActualite(String typeActualite) {
		this.typeActualite = typeActualite;
	}
	public User getWhat() {
		return what;
	}
	public void setWhat(User what) {
		this.what = what;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public WhoDto getWhoDto() {
		return whoDto;
	}
	public void setWhoDto(WhoDto whoDto) {
		this.whoDto = whoDto;
	}
	public ActualiteDto(int idActualite, String typeActualite, User what, Date date, WhoDto whoDto) {
		super();
		this.idActualite = idActualite;
		this.typeActualite = typeActualite;
		this.what = what;
		this.date = date;
		this.whoDto = whoDto;
	}
	public ActualiteDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	


}
