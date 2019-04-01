package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Autor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn
	private Korisnik idKorisnik;
	@ManyToOne
    @JoinColumn
	private Clanak idClanakA;
	
	
	public Autor(Korisnik k, Clanak c) {
		this.idKorisnik = k;
		this.idClanakA = c;
	}
	public Autor()  {
		
	}
	public Korisnik getIdKorisnik()  {
		return idKorisnik;
	}
	public Clanak getIdClanak()  {
		return idClanakA;
	}
	public void setIdKorisnik(Korisnik idK)  {
		this.idKorisnik = idK;
	}
	public void setIdClanak(Clanak idC)  {
		this.idClanakA = idC;
	}
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    


}
