package com.example.controller;

import com.example.jpa.entity.Estado;
import com.example.jpa.repository.EstadoRepository;
import com.example.model.MessageStore;
import com.opensymphony.xwork2.ActionSupport;

public class Controller01Action extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private MessageStore messageStore;
	private String userName;
	private String id;
	private String nombre;

	public Controller01Action() {
		// TODO Auto-generated constructor stub
	}
	
	public String execute() {
		this.messageStore = new MessageStore();
		System.out.println("userName from action: " + this.userName);
		
		System.out.println("A simple inocent printing");
		
		EstadoRepository estadoRepository = new EstadoRepository();
		Estado estado = estadoRepository.getEstado(Integer.parseInt(id));
		System.out.println("Controller01Action: estado: " + estado.getId() + " , " + estado.getNombre());
		
		this.nombre = estado.getNombre();
		
		return SUCCESS;
	}

	public MessageStore getMessageStore() {
		return messageStore;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}
	
	

}
