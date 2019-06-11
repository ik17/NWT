package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Komentar;


@Repository
public interface KomentarRepository extends JpaRepository<Komentar, Long>{
	@Query(value = "SELECT * FROM komentar WHERE clanak_id = :id", 
			  nativeQuery = true)
			public List<Komentar> findKomentarByIdClanak(@Param("id") Long id);
	
}
