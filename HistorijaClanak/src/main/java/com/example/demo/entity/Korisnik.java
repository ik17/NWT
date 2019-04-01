package com.example.demo.entity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "clancik", "autori"})



@Entity
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "The id must not be null!")
    private Long id;
	
	@Size(min = 3, max = 30, message = "The length of username must be between 3 and 30 characters!")
	@NotNull(message = "User must have a username!")
	private String username;
	
	 @OneToMany(mappedBy = "odobrioClanak", cascade = CascadeType.ALL)
	    private Set<Clanak> clancik;
	 
	 @OneToMany(mappedBy = "idKorisnik", cascade = CascadeType.ALL)
	    private Set<Autor> autori;
	public Korisnik() {
		
	}
	public Korisnik(String username, Set<Autor> autors, Clanak...clanaks) {
		this.username = username;
		//this.id = 1;
		this.clancik = Stream.of(clanaks).collect(Collectors.toSet());
		this.clancik.forEach(x -> x.setOdobrioClanak(this));
		//this.autori = Stream.of(autors).collect(Collectors.toSet());
		this.autori = autors;
		this.autori.forEach(x -> x.setIdKorisnik(this));
	}
	public Korisnik(String username) {
		this.username = username;
		//this.id = 1;
		//this.clancik = Stream.of(clanaks).collect(Collectors.toSet());
		//this.clancik.forEach(x -> x.setOdobrioClanak(this));
		//this.autori = Stream.of(autors).collect(Collectors.toSet());
		//this.autori = autors;
		//this.autori.forEach(x -> x.setIdKorisnik(this));
	}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }
	
	
}
