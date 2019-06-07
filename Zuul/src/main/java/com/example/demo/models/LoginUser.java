package com.example.demo.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "LoginUser")
public class LoginUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String password;
	
	private String role;
	
	private String ime;
	
	private String prezime;
	
	private String biografija;
	
	
	public LoginUser() {
	}
	
	public LoginUser(String username, String password, String role, String ime, String prezime, String biografija) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.ime = ime;
		this.prezime = prezime;
		this.biografija = biografija;
	}

	public LoginUser(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public LoginUser(LoginUser novi) {
		this.username = novi.username;
		this.password = novi.password;
		this.role = novi.role;
		this.ime = novi.ime;
		this.prezime = novi.prezime;
		this.biografija = novi.biografija;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String id) {
		this.role = id;
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

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getBiografija() {
		return biografija;
	}

	public void setBiografija(String biografija) {
		this.biografija = biografija;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}