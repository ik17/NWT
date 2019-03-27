package com.example.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "Korisnik")
public class Korisnik {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "ID must not be null!")
	private int id;
	
	private String username;
	
	private String password;
	
	@ManyToOne
	@JoinColumn
	private KorisnikPodaci korisnikPodaci;
	
	@ManyToOne
	@JoinColumn
	private UlogaKorisnik ulogaKorisnik;
	
	
	public Korisnik(String username, String password, KorisnikPodaci kp, UlogaKorisnik uk) {
		this.username = username;
		this.password = password;
		this.korisnikPodaci = kp;
		this.ulogaKorisnik = uk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
