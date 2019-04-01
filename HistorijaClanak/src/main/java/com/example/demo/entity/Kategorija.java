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

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(exclude = "clanci")

@Entity
public class Kategorija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
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
