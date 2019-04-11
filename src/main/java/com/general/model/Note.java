package com.general.model;

import java.io.Serializable;
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


@SuppressWarnings("serial")
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Note implements Serializable {
	
	@Id
	@GeneratedValue
	private int idNote;
	
	@ManyToOne	
	@JoinColumn(name="idUser")
	private User user;
	@ManyToOne
	@JoinColumn(name="idRecette")
	private Recette recette;
	@Column
	private int note;
	@Column
	private Date dateCreation;
	public Note(int idNote, User user, Recette recette, int note, Date dateCreation, Date dateModification) {
		super();
		this.idNote = idNote;
		this.user = user;
		this.recette = recette;
		this.note = note;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
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


	@Column
	private Date dateModification;
	
	public Note(int idNote, User user, Recette recette, int note) {
		super();
		this.idNote = idNote;
		this.user = user;
		this.recette = recette;
		this.note = note;
	}


	public Note() {
		super();
	}


	public int getIdNote() {
		return idNote;
	}


	public void setIdNote(int idNote) {
		this.idNote = idNote;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Recette getRecette() {
		return recette;
	}


	public void setRecette(Recette recette) {
		this.recette = recette;
	}


	public int getNote() {
		return note;
	}


	public void setNote(int note) {
		this.note = note;
	}
	
	

	
	
}



/*


idUser int,
    idRecette int,
    note int check (note between 1 and 5),
    FOREIGN KEY (idUser) REFERENCES User(idUser),
    FOREIGN KEY (idRecette) REFERENCES Recette(idRecet


*/