package com.example.DsStore.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	public ResponseEntity<ApiErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiErrorResponse errorDetails = new ApiErrorResponse(message, false);
		return new ResponseEntity<ApiErrorResponse>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles exception for invalid id.
	 *
	 * @return ResponseEntity <ApiResponse>
	 */
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> idNotFoundExceptionHandler(IdNotFoundException ex) {
		String message = ex.getMessage();
		ApiErrorResponse errorDetails = new ApiErrorResponse(message, false);
		return new ResponseEntity<ApiErrorResponse>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * It Handles all unwanted exception.
	 *
	 * @return ResponseEntity<?>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
		String message = ex.getMessage();
		ApiErrorResponse errorDetails = new ApiErrorResponse(message, false);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles MethodArgumentNotValidException.
	 *
	 * @return ResponseEntity <Map<String, String>>
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String FieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(FieldName, message);
		});

		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}

}
