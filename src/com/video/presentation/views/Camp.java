package com.video.presentation.views;

import com.video.exceptions.CampsBuitsException;

public class Camp {

	private final String question;
	private String answer;

	public Camp(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) throws CampsBuitsException {
		if (answer == null || answer.trim().equals(""))
			throw new CampsBuitsException("");
		else
			this.answer = answer;
	}
	
	public String getQuestion() {
		return this.question;
	}

}
