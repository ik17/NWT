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

	
}
