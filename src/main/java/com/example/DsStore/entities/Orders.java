package com.example.DsStore.entities;

import java.util.Date;

import com.example.DsStore.config.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private Date orderTimeStamp;
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	public void setOrderTimeStamp(Date currDate) {
		this.orderTimeStamp = currDate;
	}

	public Date getOrderTimeStamp() {
		return new Date(orderTimeStamp.getTime());
	}

}
