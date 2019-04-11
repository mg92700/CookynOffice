package com.general.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.DomainClass;
import org.jtransfo.MappedBy;
import org.jtransfo.NotMapped;

import com.general.model.User;

import lombok.Data;

@Data
@DomainClass("com.general.model.Relation")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RelationDto{

	@MappedBy
	private int idRelation;
	@MappedBy
	private User user;
	@MappedBy
	private User friend;
	@MappedBy
	private Date dateRelation;
	@NotMapped
	private String errorTxt;
	
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

	public String getErrorTxt() {
		return errorTxt;
	}

	public void setErrorTxt(String errorTxt) {
		this.errorTxt = errorTxt;
	}
	public RelationDto(int idRelation, User user, User friend, Date dateRelation, String errorTxt) {
		super();
		this.idRelation = idRelation;
		this.user = user;
		this.friend = friend;
		this.dateRelation = dateRelation;
		this.errorTxt = errorTxt;
	}

	public RelationDto() {
		super();
	}
	
}