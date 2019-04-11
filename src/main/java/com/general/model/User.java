package com.general.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.DomainClass;

import java.util.Date;

@Entity
@DomainClass("com.general.dto.UserDto")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User{
	
	@Id
	@GeneratedValue
	private Integer idUser;
	@Column
	private String nomUser;
	@Column
	private String prenomUser;
	@Column
	private String mailUser;
	@Column
	private String passwordUser;
	@Column
	private String usernameUser;
	@Column
	private String villeUser;
	@Column
	private String role;
	@Column
	private Date dateCreation;
	@Column
	private Date dateModification;
	@Column
	private int compteActive;
	@Column
	private int mailVerifier;
	@Column
	private Date dateDerniereConnection;
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getNomUser() {
		return nomUser;
	}
	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}
	public String getPrenomUser() {
		return prenomUser;
	}
	public void setPrenomUser(String prenomUser) {
		this.prenomUser = prenomUser;
	}
	public String getMailUser() {
		return mailUser;
	}
	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	public String getPasswordUser() {
		return passwordUser;
	}
	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	public String getUsernameUser() {
		return usernameUser;
	}
	public void setUsernameUser(String usernameUser) {
		this.usernameUser = usernameUser;
	}
	public String getVilleUser() {
		return villeUser;
	}
	public void setVilleUser(String villeUser) {
		this.villeUser = villeUser;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public int getCompteActive() {
		return compteActive;
	}
	public void setCompteActive(int compteActive) {
		this.compteActive = compteActive;
	}
	public int getMailVerifier() {
		return mailVerifier;
	}
	public void setMailVerifier(int mailVerifier) {
		this.mailVerifier = mailVerifier;
	}
	public Date getDateDerniereConnection() {
		return dateDerniereConnection;
	}
	public void setDateDerniereConnection(Date dateDerniereConnection) {
		this.dateDerniereConnection = dateDerniereConnection;
	}
	public User(Integer idUser, String nomUser, String prenomUser, String mailUser, String passwordUser,
			String usernameUser, String villeUser, String role, Date dateCreation, Date dateModification,
			int compteActive, int mailVerifier, Date dateDerniereConnection) {
		super();
		this.idUser = idUser;
		this.nomUser = nomUser;
		this.prenomUser = prenomUser;
		this.mailUser = mailUser;
		this.passwordUser = passwordUser;
		this.usernameUser = usernameUser;
		this.villeUser = villeUser;
		this.role = role;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
		this.compteActive = compteActive;
		this.mailVerifier = mailVerifier;
		this.dateDerniereConnection = dateDerniereConnection;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	



	
	
}
