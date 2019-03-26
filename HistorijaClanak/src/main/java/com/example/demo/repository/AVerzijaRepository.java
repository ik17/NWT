package com.example.demo.repository;


import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AVerzija;

@Repository
public interface AVerzijaRepository extends JpaRepository<AVerzija, Long> {
	AVerzija findById(int id);


}