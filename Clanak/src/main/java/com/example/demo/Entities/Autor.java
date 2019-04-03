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
	private Long id;
	
	
	@ManyToOne
	@JoinColumn
	@NotNull(message = "Korisnik ID must not be null!")
	private Korisnik korisnik;
	
	@ManyToOne
	@JoinColumn
	@NotNull(message = "Clanak ID must not be null!")
	private Clanak clanak;
	
	public Long getId() {
        return id;
    }

    public void setId(Long idAutor) {
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

	public Autor(Clanak clanak, Korisnik korisnik) {
		this.korisnik = korisnik;
		this.clanak = clanak;
	}
	
	public Autor() {
		
	}
    
    
}
