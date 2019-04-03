package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Clanak;

@Repository
public interface ClanakRepository extends JpaRepository<Clanak, Long>{
	
}
