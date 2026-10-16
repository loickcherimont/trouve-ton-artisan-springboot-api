package com.github.loickcherimont.trouve_ton_artisan.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates uncaught exceptions into the uniform {@link ApiError} JSON body.
 *
 * <p>Every unexpected failure results in an opaque 500 response: internal
 * details are logged server-side but never leaked to the client.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handles any unexpected exception raised while processing a request.
	 *
	 * @param exception the exception thrown by a controller or service
	 * @return HTTP 500 with an {@link ApiError} body
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleUnexpected(Exception exception) {
		log.error("Unexpected error while processing the request", exception);
		ApiError body = ApiError.of(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"An unexpected error occurred. Please try again later.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}

}
