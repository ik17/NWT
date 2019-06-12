package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
@Entity
public class AVerzija {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	@ManyToOne
    @JoinColumn
	private Clanak idClanak;
	@Positive
	private int verzijaClanka;
	@Size(min = 10, message = "The length of link must be min 10 characters!")
	@NotEmpty 
	private String linkNaClanak;
	//@Size(min = 20, message = "The length of review must be min 20 characters!")	
	private String reviewVerzije;
	public AVerzija(int ver, String link, String rev, Clanak c) {
		this.linkNaClanak = link;
		this.reviewVerzije = rev;
		this.verzijaClanka = ver;
		this.idClanak = c;
		
	}
	
	public int getVerzijaClanka() {
		return verzijaClanka;
	}
	public String getLink() {
		return linkNaClanak;
	}
	public String getReview() {
		return reviewVerzije;
	}
	public void setVerzijaClanka(int ver) {
		this.verzijaClanka = ver;
	}
	public void setLink(String link) {
		this.linkNaClanak = link;
	}
	public void setReview(String rev) {
		this.reviewVerzije = rev;
	}
	
	public AVerzija() {
		
	}
	
	
	public Clanak getIdClanak()  {
		return idClanak;
	}

	public void setIdClanak(Clanak idC)  {
		this.idClanak = idC;
	}
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
