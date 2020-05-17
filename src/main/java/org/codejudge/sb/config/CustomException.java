package org.codejudge.sb.config;

import org.springframework.stereotype.Component;

/**
 * @description It is use for throw custom exceptions
 *
 */
@Component
public class CustomException extends Exception {

	private static final long serialVersionUID = 6798031143363755641L;

	public CustomException(String message) {
		super(message);
	}

	public CustomException() {
		super();
	}

	public CustomException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
}