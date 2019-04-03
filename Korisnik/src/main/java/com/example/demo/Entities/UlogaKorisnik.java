package com.example.demo.Entities;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude= "korisnici")
@Entity
@Table(name = "UlogaKorisnik")
public class UlogaKorisnik {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "ID must not be null!")
	private Long id;
	
	private String ulogaKorisnik;
	
	@OneToMany(mappedBy = "ulogaKorisnik", cascade = CascadeType.ALL)
	private Set<Korisnik> korisnici;
	
	public UlogaKorisnik() {
	}
	
	public UlogaKorisnik(String uloga, Korisnik...korisnici) {
		this.ulogaKorisnik = uloga;
		this.korisnici = Stream.of(korisnici).collect(Collectors.toSet());
		this.korisnici.forEach(x -> x.setUlogaKorisnik(this));
	}

	public UlogaKorisnik(String uloga) {
		this.ulogaKorisnik = uloga;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUlogaKorisnik() {
		return ulogaKorisnik;
	}

	public void setUlogaKorisnik(String ulogaKorisnik) {
		this.ulogaKorisnik = ulogaKorisnik;
	}
	
	
}
