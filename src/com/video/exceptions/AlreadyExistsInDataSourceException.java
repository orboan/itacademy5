package com.video.exceptions;

public class AlreadyExistsInDataSourceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistsInDataSourceException(String msg) {
		super("Error: Element ja existent al data source.\n" + msg);
	}
}
