package com.example.demo.Entities;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude= {"komentari", "autori"})
@Entity
@Table(name = "Clanak")
public class Clanak {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "ID must not be null!")
	private int id;
	
	private String naziv;
	
	@ManyToOne
	@JoinColumn
	private Kategorija kategorija;
	
	private boolean clanakOdobren;
	
	@ManyToOne
	@JoinColumn
	private Korisnik odobrioClanak;
	
	@OneToMany(mappedBy = "clanak", cascade = CascadeType.ALL)
	private Set<Komentar> komentari;
	
	@OneToMany(mappedBy = "clanak", cascade = CascadeType.ALL)
	private Set<Autor> autori;
	
	public Clanak(String naziv, Kategorija kategorija, Boolean odobren, Komentar[] komentari, Autor...autori) {
		this.naziv = naziv;
		this.kategorija = kategorija;
		this.clanakOdobren = odobren;
		this.komentari = Stream.of(komentari).collect(Collectors.toSet());
		this.komentari.forEach(x -> x.setClanak(this));
		this.autori = Stream.of(autori).collect(Collectors.toSet());
		this.autori.forEach(x -> x.setClanak(this));
	}
	
	public Kategorija getKategorija() {
		return kategorija;
	}
	public void setKategorija(Kategorija kategorija) {
		this.kategorija=kategorija;
	}

	public int getId() {
		return id;
	}

	public void setIdClanak(int idClanak) {
		this.id = idClanak;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isClanakOdobren() {
		return clanakOdobren;
	}

	public void setClanakOdobren(boolean clanakOdobren) {
		this.clanakOdobren = clanakOdobren;
	}

	public Korisnik getOdobrioClanak() {
		return odobrioClanak;
	}

	public void setOdobrioClanak(Korisnik odobrioClanak) {
		this.odobrioClanak = odobrioClanak;
	}

	public Clanak(String naziv, Kategorija kategorija,
			boolean clanakOdobren, Korisnik odobrioClanak) {
		this.naziv = naziv;
		this.kategorija = kategorija;
		this.clanakOdobren = clanakOdobren;
		this.odobrioClanak = odobrioClanak;
	}
	
}
