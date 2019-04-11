package com.general.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.DomainClass;
import org.jtransfo.MappedBy;
import org.jtransfo.NotMapped;

import com.general.model.User;

import lombok.Data;

@Data
@DomainClass("com.general.model.TokenUser")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TokenUserDto {
	@MappedBy
	private int idTokenUser;
	@MappedBy
	private User user;
	@MappedBy
	private String token;
	@MappedBy
	private Date dateCreation;
	@MappedBy
	private Date dateModification;
	@NotMapped 
	private String Errortxt;
	
	public TokenUserDto(int idTokenUser, User user, String token, Date dateCreation, Date dateModification) {
		super();
		this.idTokenUser = idTokenUser;
		this.user = user;
		this.token = token;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

	
	public TokenUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getIdTokenUser() {
		return idTokenUser;
	}

	public void setIdTokenUser(int idTokenUser) {
		this.idTokenUser = idTokenUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	public String getErrortxt() {
		return Errortxt;
	}


	public void setErrortxt(String errortxt) {
		Errortxt = errortxt;
	}
	
	
}
