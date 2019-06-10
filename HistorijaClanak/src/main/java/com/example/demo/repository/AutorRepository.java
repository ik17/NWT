package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
	//Autor findById(int id);
	
	@Query(value = "SELECT * FROM autor WHERE id_clanaka_id = :id", 
			  nativeQuery = true)
			public List<Autor> findAutorByIdClanak(@Param("id") Long id);


}