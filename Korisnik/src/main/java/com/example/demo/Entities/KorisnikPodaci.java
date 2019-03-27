package com.example.demo.Entities;

import java.util.Date;
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
@Table(name = "KorisnikPodaci")
public class KorisnikPodaci {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "ID must not be null!")
	private int id;
	
	private String ime;
	
	private String prezime;
	
	private String biografija;
	
	private Date datumPrijave;

	public KorisnikPodaci(String ime, String prezime, String biografija, Date datumPrijave) {
		this.ime = ime;
		this.prezime = prezime;
		this.biografija = biografija;
		this.datumPrijave = datumPrijave;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getBiografija() {
		return biografija;
	}

	public void setBiografija(String biografija) {
		this.biografija = biografija;
	}

	public Date getDatumPrijave() {
		return datumPrijave;
	}

	public void setDatumPrijave(Date datumPrijave) {
		this.datumPrijave = datumPrijave;
	}
	
	@OneToMany(mappedBy = "korisnikPodaci", cascade = CascadeType.ALL)
	private Set<Korisnik> korisnici;

	public KorisnikPodaci(String ime, String prezime, String biografija, Date datumPrijave, Korisnik...korisnici) {
		this.ime = ime;
		this.prezime = prezime;
		this.biografija = biografija;
		this.datumPrijave = datumPrijave;
		this.korisnici = Stream.of(korisnici).collect(Collectors.toSet());
		this.korisnici.forEach(x -> x.setKorisnikPodaci(this));
	}
	
	
}
