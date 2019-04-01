package com.example.demo.entity;

import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"verzije", "autors"})

@Entity
public class Clanak {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String naziv;
	
	
	@ManyToOne
    @JoinColumn
	private Kategorija idKategorije;
	private int clanakOdobren;
	@ManyToOne
    @JoinColumn
	private Korisnik odobrioClanak;
	
	 @OneToMany(mappedBy = "idClanakA", cascade = CascadeType.ALL)
	    private Set<Autor> autors;
	
	
	 @OneToMany(mappedBy = "idClanak", cascade = CascadeType.ALL)
	    private Set<AVerzija> verzije;
	
	public Clanak() {
		
	}
	public Clanak(String naziv, Kategorija idKategorije, int clanakOdobren, Korisnik odobrioClanak, Set<Autor> autori, AVerzija...verzijas ) {
		this.naziv = naziv;
		this.idKategorije = idKategorije;
		this.clanakOdobren = clanakOdobren;
		this.odobrioClanak = odobrioClanak;
		this.verzije = Stream.of(verzijas).collect(Collectors.toSet());
		this.verzije.forEach(x -> x.setIdClanak(this));
		this.autors = autori;
		this.autors.forEach(x -> x.setIdClanak(this));
		//this.id = 1;
		
		//this.id = 1;
	}
	public Clanak(String naziv, Kategorija idKategorije, int clanakOdobren, Korisnik odobrioClanak, Set<AVerzija> verzijas, Autor...autori ) {
		this.naziv = naziv;
		this.idKategorije = idKategorije;
		this.clanakOdobren = clanakOdobren;
		this.odobrioClanak = odobrioClanak;
		//this.verzije = Stream.of(verzijas).collect(Collectors.toSet());
		this.verzije = verzijas;
		this.verzije.forEach(x -> x.setIdClanak(this));
		this.autors = Stream.of(autori).collect(Collectors.toSet());
		this.autors.forEach(x -> x.setIdClanak(this));
		//this.id = 1;
		
		//this.id = 1;
	}
	public Clanak(String naziv, Kategorija idKategorije, int clanakOdobren, Korisnik odobrioClanak ) {
		this.naziv = naziv;
		this.idKategorije = idKategorije;
		this.clanakOdobren = clanakOdobren;
		this.odobrioClanak = odobrioClanak;
		//this.verzije = Stream.of(verzijas).collect(Collectors.toSet());
		//this.verzije = verzijas;
		//this.verzije.forEach(x -> x.setIdClanak(this));
		//this.autors = Stream.of(autori).collect(Collectors.toSet());
		//this.autors.forEach(x -> x.setIdClanak(this));
		//this.id = 1;
		
		//this.id = 1;
	}
	public Kategorija getIdKategorije() {
		return idKategorije;
	}
	public void setIdKategorije(Kategorija idKategorije) {
		this.idKategorije = idKategorije;
	}
	public int getClanakOdobren() {
		return clanakOdobren;
	}
	public void setClanakOobren(int idKategorije) {
		this.clanakOdobren = idKategorije;
	}
	public Korisnik getOdobrioClanak() {
		return odobrioClanak;
	}
	public void setOdobrioClanak(Korisnik idKategorije) {
		this.odobrioClanak = idKategorije;
	}
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNaziv() {
        return this.naziv;
    }

    public void setNaziv(String id) {
        this.naziv = id;
    }
	

}
