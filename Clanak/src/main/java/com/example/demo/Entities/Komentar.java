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
@Table(name = "Komentar")
public class Komentar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "ID must not be null!")
	private int id;
	
	private String textKomentara;
	
	@ManyToOne
	@JoinColumn
	private Clanak clanak;
	
	@ManyToOne
	@JoinColumn
	private Korisnik korisnik;
	
	public Clanak getClanak() {
		return clanak;
	}
	public void setClanak(Clanak clanak) {
		this.clanak = clanak;
	}
	public int getId() {
		return id;
	}
	public void setId(int idKomentar) {
		this.id = idKomentar;
	}
	public String getTextKomentara() {
		return textKomentara;
	}
	public void setTextKomentara(String textKomentara) {
		this.textKomentara = textKomentara;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public Komentar(String textKomentara, Clanak clanak, Korisnik korisnik) {
		this.textKomentara = textKomentara;
		this.clanak = clanak;
		this.korisnik = korisnik;
	}
	
	
}
