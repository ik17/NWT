package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class AVerzija {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	@ManyToOne
    @JoinColumn
	private Clanak idClanak;
	private int verzijaClanka;
	private String linkNaClanak;
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
