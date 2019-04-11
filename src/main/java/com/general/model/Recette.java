package com.general.model;

import java.sql.Blob;
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
public class Recette{
	
	@Id
	@GeneratedValue
	private Integer idRecette;
	@Column
	private String catRecette;
	@Column
	private String libelleRecette;
	@Column
	private int tempPrepaRecette;
	@Column
	private String diffRecette;
	@ManyToOne
	@JoinColumn(name="creeparUser")
	private User user;
	@Column
	private String urlRecette;
	@Column
	private Integer prix;
	@Column
	private Date dateCreation;
	
	
	
	public Recette(Integer idRecette, String catRecette, String libelleRecette, int tempPrepaRecette,
			String diffRecette, User user, String urlRecette, Integer prix, Date dateCreation) {
		super();
		this.idRecette = idRecette;
		this.catRecette = catRecette;
		this.libelleRecette = libelleRecette;
		this.tempPrepaRecette = tempPrepaRecette;
		this.diffRecette = diffRecette;
		this.user = user;
		this.urlRecette = urlRecette;
		this.prix = prix;
		this.dateCreation = dateCreation;
		
	}

	public String getUrlRecette() {
		return urlRecette;
	}

	public void setUrlRecette(String urlRecette) {
		this.urlRecette = urlRecette;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Recette(Integer prix) {
		super();
		this.prix = prix;
	}

	public Integer getPrix() {
		return prix;
	}

	public void setPrix(Integer prix) {
		this.prix = prix;
	}



	public Recette() {
		super();
	}
	
	public Integer getIdRecette() {
		return idRecette;
	}
	public void setIdRecette(Integer idRecette) {
		this.idRecette = idRecette;
	}
	public String getCatRecette() {
		return catRecette;
	}
	public void setCatRecette(String catRecette) {
		this.catRecette = catRecette;
	}
	public String getLibelleRecette() {
		return libelleRecette;
	}
	public void setLibelleRecette(String libelleRecette) {
		this.libelleRecette = libelleRecette;
	}
	public int getTempPrepaRecette() {
		return tempPrepaRecette;
	}
	public void setTempPrepaRecette(int tempPrepaRecette) {
		this.tempPrepaRecette = tempPrepaRecette;
	}
	public String getDiffRecette() {
		return diffRecette;
	}
	public void setDiffRecette(String diffRecette) {
		this.diffRecette = diffRecette;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}


/*

	idRecette int primary key auto_increment,
    catRecette varchar(50),
    libelleRecette varchar(50),
    tempPrepaRecette int,
    diffRecette varchar(10) check (diffRecette in ('Facile','Moyen','Difficle')),
    creeparUser int,
	datecreationidUser date,
    photoRecette blob,
    FOREIGN KEY (creeparUser) REFERENCES User(idUser)

*/