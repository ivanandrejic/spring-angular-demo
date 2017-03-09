package com.toptal.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity(name = "TIME_ZONE")
public class TimeZone {
	
	private @Id @GeneratedValue Long id;
	
	@Column(name="ZONE_OFFSET")
	@NotNull @Min(-12) @Max(+14)
	private Integer offset;
	
	@Column(name="NAME")
	@NotNull
	@Size(min=2, max=30)
	private String name;
	
	@Column(name="CITY")
	private String city;
	
//	@ManyToOne
//	@JoinColumn(name="USER_ID")
	@Column(name="USER_ID")
	private Long userId;
	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
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
