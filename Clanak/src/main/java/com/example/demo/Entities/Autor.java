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
@Table(name = "Autor")
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "ID must not be null!")
	private int id;
	
	//fk
	@ManyToOne
	@JoinColumn
	private Korisnik korisnik;
	//fk
	@ManyToOne
	@JoinColumn
	private Clanak clanak;
	
	public int getId() {
        return id;
    }

    public void setId(int idAutor) {
        this.id = idAutor;
    }
    
    public void setClanak(Clanak clanak) {
    	this.clanak = clanak;
    }
    
    public Clanak getClanak() {
    	return clanak;
    }


	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Autor(Korisnik korisnik, Clanak clanak) {
		this.korisnik = korisnik;
		this.clanak = clanak;
	}
    
    
}
