package com.toptal.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@Entity(name = "SECURE_USER")
public class SecureUser {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	public static final String DEFAULT_PASSWORD = "toptal";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_CREATE_USER = "ROLE_CREATE_USER";
	public static final String ROLE_USER_MANAGER = "ROLE_USER_MANAGER";
	public static final String ROLE_USER = "ROLE_USER";

	private @Id @GeneratedValue Long id;
	
	private String password;

	@Column(name = "NAME", unique = true)
	@Size(min=4, max=30) 
	private String name;
	
	@Column(name = "ROLE")
	private String role;
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
