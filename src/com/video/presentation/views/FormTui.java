package com.video.presentation.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.video.presentation.controllers.MainController;
import com.video.presentation.i18n.Messages;

public abstract class FormTui {
	
	protected Scanner in;
	protected MainController controller;
	protected Messages messages = Messages.getInstance();

	protected List<Camp> camps = new ArrayList<>();
	
	public abstract void show();
	
	protected void printQuestion(Camp camp) {
		System.out.print(camp.getQuestion());
	}
	public List<Camp> getCamps(){
		return this.camps;
	}
	
}
