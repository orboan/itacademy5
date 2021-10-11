package com.video.exceptions;

public class MenuOutOfBoundsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuOutOfBoundsException(String msg) {
		super("Error: Opció seleccionada no vàlida.\n" + msg);
	}
	
}
