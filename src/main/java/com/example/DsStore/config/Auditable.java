package com.example.DsStore.config;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date orderTimeStamp;

}
