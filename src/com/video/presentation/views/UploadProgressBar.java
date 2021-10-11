package com.video.presentation.views;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class UploadProgressBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JProgressBar pbar;
	
	public final int MIN = 0;
	public final int MAX;
	
	public UploadProgressBar(int max) {
		this.MAX = max;
		pbar = new JProgressBar();
		pbar.setMinimum(MIN);
		pbar.setMaximum(MAX);
		add(pbar);
	}
	
	public void updateBar(int newValue) {
		pbar.setValue(newValue);
	}
}
