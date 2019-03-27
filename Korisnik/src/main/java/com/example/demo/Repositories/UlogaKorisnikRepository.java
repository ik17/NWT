package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.UlogaKorisnik;


@Repository
public interface UlogaKorisnikRepository extends JpaRepository<UlogaKorisnik, Integer> {
	UlogaKorisnik findById(int id);
}
