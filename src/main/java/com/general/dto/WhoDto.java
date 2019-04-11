package com.general.dto;

import org.jtransfo.MappedBy;

public class WhoDto {
	
	@MappedBy
	private int id;
	@MappedBy
	private String name;
	@MappedBy
	private String type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public WhoDto(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public WhoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
