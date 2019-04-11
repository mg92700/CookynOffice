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
public class Planning{
	
	@Id
	@GeneratedValue
	private int idPlanning;
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	@ManyToOne
	@JoinColumn(name="idRecette")
	private Recette recette;
	@Column
	private Date datePlanning;
	
	public Planning(int idPlanning, User user, Recette recette, Date datePlanning) {
		super();
		this.idPlanning = idPlanning;
		this.user = user;
		this.recette = recette;
		this.datePlanning = datePlanning;
	}
	public Planning() {
		super();
	}
	public int getIdPlanning() {
		return idPlanning;
	}
	public void setIdPlanning(int idPlanning) {
		this.idPlanning = idPlanning;
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
	public Date getDatePlanning() {
		return datePlanning;
	}
	public void setDatePlanning(Date datePlanning) {
		this.datePlanning = datePlanning;
	}
	
	
}



/*


	idPlanning int primary key auto_increment,
	idUser int,
	idRecette int,
    datePlanning datetime,
    FOREIGN KEY (idUser) REFERENCES User(idUser),
    FOREIGN KEY (idRecette) REFERENCES Recette(idRecette)


*/