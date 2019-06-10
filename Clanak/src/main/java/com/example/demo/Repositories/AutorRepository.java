package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Autor;
import com.example.demo.Entities.Clanak;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
	
	@Query(value = "SELECT * FROM autor WHERE clanak_id = :id", 
			  nativeQuery = true)
			public List<Autor> findAutorByIdClanak(@Param("id") Long id);
	
}
