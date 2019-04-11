package com.general.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jtransfo.NotMapped;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseCategorieDto {
	
	
	
	@NotMapped 
	List<IngredientCourse> listCourseDto;
	
	@NotMapped 
	String categorie;

	public List<IngredientCourse> getListCourseDto() {
		return listCourseDto;
	}

	public void setListCourseDto(List<IngredientCourse> listCourseDto) {
		this.listCourseDto = listCourseDto;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public CourseCategorieDto(List<IngredientCourse> listCourseDto, String categorie) {
		super();
		this.listCourseDto = listCourseDto;
		this.categorie = categorie;
	}

	public CourseCategorieDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
