package com.example.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estado")
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique=true)
	private Integer id;
	
	@Column(name="nombre", nullable=false)
	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}
