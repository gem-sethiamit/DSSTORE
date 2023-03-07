package com.example.DsStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	@NotEmpty
	@Size(min = 4, message = "Customer name must be min of 4 characters !!")
	private String customerName;
	@NotEmpty
	@Size(min = 5, message = "Customer Address must be min of 8 characters")
	private String customerAddress;
	@NotNull
	private int customerNumber;
}
