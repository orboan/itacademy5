package com.video.exceptions;

public class BadDataSourceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadDataSourceException(String msg) {
		super("Error: Invalid or not set DataSource.\n" + msg);
	}
}
