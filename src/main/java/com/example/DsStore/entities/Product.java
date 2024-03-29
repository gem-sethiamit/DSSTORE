package com.example.DsStore.entities;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	@NotEmpty
	@Size(min = 4, message = "Product name must be min of 4 characters !!")
	private String productName;
	@NotEmpty
	@Size(min = 5, message = "Product Desc must be min of 8 characters")
	private String productDesc;
	@NotNull
	private int price;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date expiry;
	@NotNull
	@Positive(message = "Count should be greater than 0")
	private int count;
	@NotNull
	private boolean avalibity;
}
