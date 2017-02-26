package com.toptal.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "TIME_ZONE")
public class TimeZone {
	
	private @Id @GeneratedValue Long id;
	
	@Column(name="TIME_ZONE")
	@NotNull
	private Date timeZone;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CITY")
	private String city;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private SecureUser user;

}
