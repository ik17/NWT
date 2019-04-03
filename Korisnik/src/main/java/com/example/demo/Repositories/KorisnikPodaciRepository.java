package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.KorisnikPodaci;
@Repository
public interface KorisnikPodaciRepository extends JpaRepository<KorisnikPodaci, Long> {
	//KorisnikPodaci findById(int id);
}
