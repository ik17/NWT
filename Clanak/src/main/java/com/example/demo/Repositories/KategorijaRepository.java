package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Kategorija;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Integer>{
	Kategorija findById(int id);
}

