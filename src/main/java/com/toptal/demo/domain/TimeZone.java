package com.toptal.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
//	@ManyToOne
//	@JoinColumn(name="USER_ID")
	@Column(name="USER_ID")
	private Long userId;

	public Date getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Date timeZone) {
		this.timeZone = timeZone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
