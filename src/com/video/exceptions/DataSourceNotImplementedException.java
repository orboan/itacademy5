package com.video.exceptions;

public class DataSourceNotImplementedException extends NullPointerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataSourceNotImplementedException(String msg) {
		super("Error: Not implemented data source.\n" + msg);
	}
}
