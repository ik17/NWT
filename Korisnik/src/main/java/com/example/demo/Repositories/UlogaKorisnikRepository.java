package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.UlogaKorisnik;


@Repository
public interface UlogaKorisnikRepository extends JpaRepository<UlogaKorisnik, Long> {
	//UlogaKorisnik findById(int id);
	@Query(value="SELECT * FROM uloga_korisnik uk WHERE uk.uloga_korisnik = :username", nativeQuery = true)
	UlogaKorisnik findByUsername(@Param("username")String username);
}
