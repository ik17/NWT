package com.example.demo.Entities;

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
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "clanci")
@Entity
@Table(name = "Kategorija")
public class Kategorija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 30, message = "Category name must be at least 2 character long, and must not surpass 30 characters in length!")
	private String naziv;
	
	@OneToMany(mappedBy = "kategorija", cascade = CascadeType.ALL)
	private Set<Clanak> clanci;
	
	public Kategorija(String naziv, Clanak... clanci){
		this.naziv = naziv;
		this.clanci = Stream.of(clanci).collect(Collectors.toSet());
		this.clanci.forEach(x -> x.setKategorija(this));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idKategorija) {
		this.id = idKategorija;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Kategorija(String naziv, Set<Clanak> clanci) {
		this.naziv = naziv;
		this.clanci = clanci;
	}
	
	public Kategorija(){
		
	}
	
	
}
