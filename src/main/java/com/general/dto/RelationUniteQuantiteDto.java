package com.general.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.MappedBy;
import org.jtransfo.NotMapped;

import com.general.model.Unite;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RelationUniteQuantiteDto {
	
	
	@NotMapped 
	private float quantite;
	
	@NotMapped
	private Unite unite;

	public float getQuantite() {
		return quantite;
	}

	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}



	public Unite getUnite() {
		return unite;
	}

	public void setUnite(Unite unite) {
		this.unite = unite;
	}

	public RelationUniteQuantiteDto(float quantite, Unite unite) {
		super();
		this.quantite = quantite;
		this.unite = unite;
	}

	public RelationUniteQuantiteDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
