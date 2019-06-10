package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
	
	
	
}
