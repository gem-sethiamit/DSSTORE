package com.example.DsStore.entities;


import jakarta.persistence.*;
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
    @Column(nullable = false,length = 100)
    String customerName;
    String customerAddress;
    int customerNumber;
}
