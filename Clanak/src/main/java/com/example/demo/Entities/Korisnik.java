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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "Korisnik")
public class Korisnik {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "User must have a username!")
	@Size(min = 3, max = 30, message = "Username must be at least 3 characters long, and up to 30 characters total!")
	private String username;

	public Long getId() {
		return id;
	}

	public void setIdKorisnik(Long idKorisnik) {
		this.id = idKorisnik;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Korisnik(String username) {
		this.username = username;
	}
	public Korisnik() {
	
	}
	@OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
	private Set<Autor> autori;
	
	@OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
	private Set<Komentar> komentari;
	
	@OneToMany(mappedBy = "odobrioClanak", cascade = CascadeType.ALL)
	private Set<Clanak> odobrio;

	public Korisnik(String username, Komentar[] komentari, Clanak[] odobrio, Autor... autori) {
		this.username = username;
		this.autori = Stream.of(autori).collect(Collectors.toSet());
		this.autori.forEach(x -> x.setKorisnik(this));
		this.komentari = Stream.of(komentari).collect(Collectors.toSet());
		this.komentari.forEach(x -> x.setKorisnik(this));
		this.odobrio = Stream.of(odobrio).collect(Collectors.toSet());
		this.odobrio.forEach(x -> x.setOdobrioClanak(this));
	}
	
	
}
