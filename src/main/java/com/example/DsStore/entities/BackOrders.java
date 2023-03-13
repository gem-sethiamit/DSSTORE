package com.example.DsStore.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BackOrders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int backOrderId;

	private Date backOrderTimeStamp;

	private int quantity;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Customer customer;

	public void setBackOrderTimeStamp(Date currDate) {
		this.backOrderTimeStamp = currDate;
	}

	public Date getBackOrderTimeStamp() {
		return new Date(backOrderTimeStamp.getTime());
	}

}
