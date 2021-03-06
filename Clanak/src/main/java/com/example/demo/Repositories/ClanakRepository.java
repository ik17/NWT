package com.example.demo.Repositories;


import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Clanak;

@Repository
public interface ClanakRepository extends JpaRepository<Clanak, Long>{
	
	public Clanak findByNaziv(String naziv);
	
	
	
	@Query(value = "SELECT * FROM clanak WHERE naziv LIKE CONCAT('%',:naziv,'%')", 
	  nativeQuery = true)
	public List<Clanak> findClanakByNaziv(@Param("naziv") String naziv);
	
	@Query(value = "SELECT * FROM clanak c, kategorija k WHERE c.kategorija_id = k.id and k.naziv LIKE CONCAT('%',:naziv,'%')", 
			  nativeQuery = true)
			public List<Clanak> findClanakByKategorija(@Param("naziv") String naziv);
	
	@Query(value = "SELECT * FROM clanak c, autor a WHERE a.korisnik_id = :id and a.clanak_id = c.id", 
			  nativeQuery = true)
	public List<Clanak> getByAuthor(@Param("id") int id);
	
}
