package com.example.DsStore.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
	private String message;
	private boolean success;

	public ApiResponse(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
}
