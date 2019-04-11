package com.general.model;

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

@Entity
@DomainClass("com.general.dto.TokenUserDto")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TokenUser {
	@Id
	@GeneratedValue
	private int idTokenUser;
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	@Column
	private String token;
	@Column
	private Date dateCreation;
	@Column
	private Date dateModification;
	
	
	public TokenUser(int idTokenUser, User user, String token, Date dateCreation, Date dateModification) {
		super();
		this.idTokenUser = idTokenUser;
		this.user = user;
		this.token = token;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}
	
	public TokenUser() {
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
	
	
}
