package com.general.dto;

import org.jtransfo.MappedBy;

public class UserRecetteDeleteDto {
	
	@MappedBy
	private Integer idUser;
	@MappedBy
	private Integer idRecette;
	@MappedBy
	private String password;
	
	public UserRecetteDeleteDto(Integer idUser, Integer idRecette, String password) {
		super();
		this.idUser = idUser;
		this.idRecette = idRecette;
		this.password = password;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public Integer getIdRecette() {
		return idRecette;
	}
	public void setIdRecette(Integer idRecette) {
		this.idRecette = idRecette;
	}
	public UserRecetteDeleteDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
