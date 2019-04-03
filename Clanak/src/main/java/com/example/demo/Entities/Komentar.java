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
@Table(name = "Komentar")
public class Komentar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Comment text must not be empty!")
	@Size(min = 3, max = 2000, message = "Comment must not be shorter than 3 characters, and cannot surpass the length of 2000 characters!")
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
	public Long getId() {
		return id;
	}
	public void setId(Long idKomentar) {
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
	public Komentar() {
		
	}
	
	
}
