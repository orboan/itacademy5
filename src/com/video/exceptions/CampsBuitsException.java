package com.video.exceptions;

public class CampsBuitsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampsBuitsException(String msg) {
		super("Error: No es permeten camps buits.\n" + msg);
	}
}
