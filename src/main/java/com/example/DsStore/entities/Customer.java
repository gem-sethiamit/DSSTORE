package com.example.DsStore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int customerId;
	@NotEmpty
	@Size(min = 4, message = "Customer name must be min of 4 characters !!")
	String customerName;
	@NotEmpty
	@Size(min = 8, message = "Customer Address must be min of 8 characters")
	String customerAddress;

	int customerNumber;
}
