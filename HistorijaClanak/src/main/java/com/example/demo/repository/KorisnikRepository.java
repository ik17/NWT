package com.example.demo.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
	//Korisnik findById(int id);
	
	//@Query(value = "INSERT INTO korisnik (username) values (?1) ", nativeQuery = true)
	//public void addKorisnik( String Ime); 
	/*
	@PersistenceContext
    private EntityManager entityManager;
	
	@Transactional
	public void insertWithQuery(Korisnik person) {
	    entityManager.createNativeQuery("INSERT INTO korisnik (username) VALUES (?)")
	      .setParameter(1, person.getUsername())
	      .executeUpdate();
	}*/

}
