package com.example.demo.entity;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name="journey")
@Entity
@Setter
@Getter
@ToString
public class Journey {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="Name can not be null")
	@Size(min=2, message="Name must not be less than two characters")
	private String name;
	
	@NotNull(message="Country can not be null")
	@Size(min=2, message="Country name must not be less than two characters")
	private String country;
	
	@Digits(fraction = 0, integer = 4, message = "Only digits are accepted")
	private Long year;	
	
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="weatherFK")
	private Weather weather;
	
	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Date createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Date updatedAt;

}
