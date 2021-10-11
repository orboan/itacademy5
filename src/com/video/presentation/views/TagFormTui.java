package com.video.presentation.views;

import java.util.Scanner;

import com.video.business.BusinessFacade;
import com.video.business.IBusinessFacade;
import com.video.domain.Tag;
import com.video.exceptions.CampsBuitsException;
import com.video.presentation.controllers.MainController;

public class TagFormTui extends FormTui {

	private Tag tag;

	public TagFormTui(Scanner in, MainController controller) {
		this.in = in;
		this.controller = controller;
		init();
	}

	private void init() {
		Camp c1 = new Camp(messages.tagQuestion1);
		camps.add(c1);
	}

	@Override
	public void show() {
		boolean continueAsking;
		for (Camp c : camps) {
			continueAsking = true;
			while (continueAsking) {
				printQuestion(c);
				try {
					c.setAnswer(in.nextLine());
					continueAsking = false;
				} catch (CampsBuitsException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
