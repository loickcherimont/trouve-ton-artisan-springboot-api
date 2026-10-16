package com.github.loickcherimont.trouve_ton_artisan.exception;

/**
 * Uniform error payload returned by the API whenever a request fails.
 *
 *
 * @param error   the HTTP status code
 * @param message a human-readable explanation of the error
 */
public record ApiError(int error, String message) {

	/**
	 * Builds an {@link ApiError} from an HTTP status code and a message.
	 *
	 * @param status  the HTTP status code, e.g. {@code 404}
	 * @param message a human-readable explanation of the error
	 * @return a new immutable error payload
	 */
	public static ApiError of(int status, String message) {
		return new ApiError(status, message);
	}

}
