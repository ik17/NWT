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
@EqualsAndHashCode(exclude = "clanci")

@Entity
public class Kategorija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "The id must not be null!")
    private Long id;
	
	@Size(min = 2, max = 30, message = "The length of name od category must be between 2 and 30 characters!")	
	private String naziv;
	
	 @OneToMany(mappedBy = "idKategorije", cascade = CascadeType.ALL)
	    private Set<Clanak> clanci;
	public Kategorija() {
		
	}
	public Kategorija(String naziv, Clanak...clanaks) {
		this.naziv = naziv;
		this.clanci = Stream.of(clanaks).collect(Collectors.toSet());
		this.clanci.forEach(x -> x.setIdKategorije(this));
		//this.id = 1;
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
