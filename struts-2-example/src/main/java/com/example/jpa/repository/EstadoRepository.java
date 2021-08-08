package com.example.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import com.example.jpa.entity.Estado;

public class EstadoRepository {
	
	private static EntityManagerFactory entityManagerFactory;
	
	public EstadoRepository () {
		super();
		
		// org.hibernate.dialect.MySQL8Dialect;
		
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("struts2jpa");	
		} catch (Exception e) {
			System.out.println("error en EstadoRepository Constructor: " + e);
			e.printStackTrace();
		}
			
	}
	
	 
	// private static EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createEntityManagerFactory("struts2jpa");
	
	
	public Estado getEstado(Integer id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String query = "SELECT e FROM Estado e WHERE id = :id";
		
		TypedQuery<Estado> typedQuery = entityManager.createQuery(query, Estado.class);
		typedQuery.setParameter("id", id);
		
		Estado estado = null;
		try {
			estado = typedQuery.getSingleResult();
			System.out.println("EstadoRepository: id, estado: " + estado.getId() + " , " + estado.getNombre() );
			
			entityManagerFactory.close();
			
			entityManager.close();
			
			return estado;
		} catch (Exception e) {
			System.out.println("EstadoRepository: " + e);
			return null;
		}
	}

}
