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

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Actualite {
	
	
	@Id
	@GeneratedValue
	private int idActualite;
	@Column
	private String typeActualite;
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	@Column
	private int idWhat;//1create add , 2 follow , 3 unfollow, 4 favoris
	@Column
	private Date date;
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getIdWhat() {
		return idWhat;
	}
	public void setIdWhat(int idWhat) {
		this.idWhat = idWhat;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Actualite() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Actualite(int idActualite, String typeActivite, User user, int idWhat, Date date) {
		super();
		this.idActualite = idActualite;
		this.typeActualite = typeActivite;
		this.user = user;
		this.idWhat = idWhat;
		this.date = date;
	}
	
	
	

}
