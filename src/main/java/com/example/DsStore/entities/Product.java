package com.example.DsStore.entities;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	@NotEmpty
	@Size(min = 4, message = "Product name must be min of 4 characters !!")
	private String productName;
	@NotEmpty
	@Size(min = 5, message = "Product Desc must be min of 8 characters")
	private String productDesc;
	@NotEmpty(message = "Please Enter Price")
	private int price;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date expiry;
	@NotEmpty
	private int count;
	@NotEmpty
	private boolean Avalibity;

}
