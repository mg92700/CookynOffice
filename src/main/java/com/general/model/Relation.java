package com.general.model;

import java.sql.Date;

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
@XmlRootElement
@DomainClass("com.general.dto.RelationDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Relation{
	
	@Id
	@GeneratedValue
	private int idRelation;
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	@ManyToOne
	@JoinColumn(name="idFriend")
	private User friend;
	@Column
	private Date dateRelation;
	
	public Relation(int idRelation, User user, User friend, Date dateRelation) {
		super();
		this.idRelation = idRelation;
		this.user = user;
		this.friend = friend;
		this.dateRelation = dateRelation;
	}

	public Relation() {
		super();
	}

	public int getIdRelation() {
		return idRelation;
	}

	public void setIdRelation(int idRelation) {
		this.idRelation = idRelation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public Date getDateRelation() {
		return dateRelation;
	}

	public void setDateRelation(Date dateRelation) {
		this.dateRelation = dateRelation;
	}
	
}


/*


	idRelation int primary key auto_increment,
    idUser int,
    idFriend int,
    dateRelation datetime,
    FOREIGN KEY (idUser) REFERENCES User(idUser),
    FOREIGN KEY (idFriend) REFERENCES User(idUser)


*/