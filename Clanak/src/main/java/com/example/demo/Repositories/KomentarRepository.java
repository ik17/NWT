package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Komentar;


@Repository
public interface KomentarRepository extends JpaRepository<Komentar, Integer>{
	Komentar findById(int id);
}
