package com.example.DsStore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles exception when no resource found.
	 *
	 * @return ResponseEntity <ApiResponse>
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse errorDetails = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * It Handles all unwanted exception.
	 *
	 * @return ResponseEntity<?>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		String message = ex.getMessage();
		ApiResponse errorDetails = new ApiResponse(message, false);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

}
