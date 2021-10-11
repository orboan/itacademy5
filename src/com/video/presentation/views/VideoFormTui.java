package com.video.presentation.views;

import java.util.Scanner;

import com.video.exceptions.CampsBuitsException;
import com.video.presentation.controllers.MainController;

public class VideoFormTui extends FormTui {

	

	public VideoFormTui(Scanner in, MainController controller) {
		this.in = in;
		this.controller = controller;
		init();
	}

	private void init() {
		Camp c1 = new Camp(messages.videoQuestion1);
		Camp c2 = new Camp(messages.videoQuestion2);
		Camp c3 = new Camp(messages.videoQuestion3);
		camps.add(c1);
		camps.add(c2);	
		camps.add(c3);	
	}

	@Override
	public void show() {

		boolean continueAsking;
		int count = 0;
		for (Camp c : camps) {
			continueAsking = true;
			while (continueAsking) {
				printQuestion(c);
				try {
					String response = in.nextLine();
					int seconds = 0;
					if(count == 2) {
						seconds = Integer.parseInt(response);
					}
					if(seconds < 0) {
						throw new Exception("\nError: la durada no pot ser negativa");
					}
					c.setAnswer(response);
					continueAsking = false;
				} catch (CampsBuitsException e) {
					System.out.println(e.getMessage());
				} catch (NumberFormatException e) {
					System.out.println("\nError: has d'introduir un número");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			count++;
		}
	}

}
