package com.example.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "Korisnik")
public class Korisnik {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "ID must not be null!")
	private Long id;
	
	@NotNull(message = "User must have a username!")
	@Size(min = 3, max = 30, message = "The length of username must be between 2 and 30 characters!")
	private String username;
	
	@Size(min = 0, max = 300, message = "The length of password must be between 6 and 30 characters!")
	private String password;
	
	@ManyToOne
	@JoinColumn
	private KorisnikPodaci korisnikPodaci;
	
	@ManyToOne
	@JoinColumn
	private UlogaKorisnik ulogaKorisnik;
	
	public Korisnik() {
	}
	
	public Korisnik(String username, String password, KorisnikPodaci kp, UlogaKorisnik uk) {
		this.username = username;
		this.password = password;
		this.korisnikPodaci = kp;
		this.ulogaKorisnik = uk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public KorisnikPodaci getKorisnikPodaci() {
		return korisnikPodaci;
	}

	public void setKorisnikPodaci(KorisnikPodaci korisnikPodaci) {
		this.korisnikPodaci = korisnikPodaci;
	}

	public UlogaKorisnik getUlogaKorisnik() {
		return ulogaKorisnik;
	}

	public void setUlogaKorisnik(UlogaKorisnik ulogaKorisnik) {
		this.ulogaKorisnik = ulogaKorisnik;
	}
	
	
}
