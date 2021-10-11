package com.video.presentation.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.CampsBuitsException;
import com.video.exceptions.MenuOutOfBoundsException;
import com.video.persistence.repos.IDataSource;
import com.video.presentation.controllers.TagController;
import com.video.presentation.controllers.UsuariController;
import com.video.presentation.controllers.VideoController;
import com.video.presentation.i18n.Messages;

public class MenuTui {

	protected final Scanner in;

	protected final Messages messages = Messages.getInstance();

	protected String decoration;
	protected String header;
	protected String question;
	protected String[] options;
	protected String footer;
	protected String selection; // Guarda l'opció seleccionada (escrit per l'usuari)
	protected String selectedTextOption; // Guarda tot el text de l'opció seleccionada
	protected int selectedOption = -99; // Guarda l'opció seleccionada en format numèric

	protected boolean quit;

	public MenuTui(Scanner in) {
		this.in = in;
	}

	public void show() {
		printIfNotNull(decoration);
		printIfNotNull(header);
		printIfNotNull(question);
		System.out.println("");
		int counter = 1;
		for (String option : options) {
			printIfNotNull("\n" + counter + "- " + option);
			counter++;
		}
		printIfNotNull(footer);

		this.selection = in.nextLine();
		checkSelection();
		if (this.selectedOption == 0)
			this.quit = true;
		else if (this.selectedOption > 0) {
			this.selectedTextOption = this.options[this.selectedOption - 1];
		}
		
		clearScreen();
		 
	}

	private void clearScreen() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}

	// Per cridar després de run()
	public int getSelectedOption() {
		return this.selectedOption;
	}

	public String getSelectedTextOption() {
		return this.selectedTextOption;
	}

	// Per saber si cal sortir del programa
	public boolean isQuit() {
		return this.quit;
	}

	// Per saber la mida de options (cas que la llista estigui buida)
	public int size() {
		return options.length;
	}

	// Setters
	public void setHeader(String header) {
		this.header = header;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	public void setOptionsFromDataSource(String bean, IDataSource ds, Usuari user) {
		if (user == null)
			switch (bean) {
			case "Tag":
				fillOptionsWithExistingTags(ds);
				break;
			case "Video":
				fillOptionsWithExistingVideos(ds);
				break;
			case "Usuari":
				fillOptionsWithExistingUsers(ds);
				break;
			default:
				break;
			}
		else {
			fillOptionsWithExistingVideos(ds, user);
		}
	}

	public void setOptionsFromDataSource(String bean, IDataSource ds) {
		setOptionsFromDataSource(bean, ds, null);
	}

	// Helpers:

	private void fillOptionsWithExistingTags(IDataSource ds) {
		TagController controller = TagController.getInstance(this, ds);
		List<String> tagOptionsList = new ArrayList<>();
		try {
			for (Tag t : controller.getAllTags()) {
				tagOptionsList.add(t.getWord());
			}
			tagOptionsList.add(messages.quitOption);
			String[] options = new String[tagOptionsList.size()];
			setOptions(tagOptionsList.toArray(options));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void fillOptionsWithExistingVideos(IDataSource ds) {
		VideoController controller = VideoController.getInstance(this, ds);
		List<String> videoOptionsList = new ArrayList<>();
		try {
			for (Video v : controller.getAllVideos()) {
				videoOptionsList.add(v.getTitle() + " - Url: " + v.getUrl());
			}
			videoOptionsList.add(messages.quitOption);
			String[] options = new String[videoOptionsList.size()];
			setOptions(videoOptionsList.toArray(options));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void fillOptionsWithExistingVideos(IDataSource ds, Usuari user) {
		VideoController controller = VideoController.getInstance(this, ds);
		List<String> videoOptionsList = new ArrayList<>();
		try {
			for (Video v : controller.getAllVideos(user)) {
				videoOptionsList.add(v.getTitle() + " - Url: " + v.getUrl());
			}
			videoOptionsList.add(messages.quitOption);
			String[] options = new String[videoOptionsList.size()];
			setOptions(videoOptionsList.toArray(options));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void fillOptionsWithExistingUsers(IDataSource ds) {
		UsuariController controller = UsuariController.getInstance(this, ds);
		List<String> userOptionsList = new ArrayList<>();
		try {
			for (Usuari u : controller.getAllUsuaris()) {
				userOptionsList.add(u.getName() + " " + u.getSurname());
			}
			userOptionsList.add(messages.quitOption);
			String[] options = new String[userOptionsList.size()];
			setOptions(userOptionsList.toArray(options));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void printIfNotNull(String str) {
		if (str != null)
			System.out.print(str);
	}

	private void checkSelection() {
		try {
			this.selectedOption = -99;
			if(this.selection.trim().equals(""))
				throw new CampsBuitsException("");
			/*
			 * Lletres permeses: q = sortir s = sí n = no
			 */
			if (this.selection.toLowerCase().equals("q")) // sortir
				this.selectedOption = 0; // Sortir
			else {
				if (this.selection.equalsIgnoreCase("s")) {
					Stream<String> opts = Arrays.stream(options);
					String foundStr = opts.filter(str -> str.contains(messages.yesOption)).findAny().orElse(null);
					if (foundStr != null) {
						this.selectedOption = -1; // Sí
					}
				} else if (this.selection.equalsIgnoreCase("n")) {
					Stream<String> opts = Arrays.stream(options);
					String foundStr = opts.filter(str -> str.contains(messages.noOption)).findAny().orElse(null);
					if (foundStr != null) {
						this.selectedOption = -2; // No
					}
				}
			}
			if (this.selectedOption == -99) { // No és cap de les lletres permeses
				this.selectedOption = Integer.parseInt(this.selection); // si és un numèric
				if (this.selectedOption < 0 || this.selectedOption > options.length) // si està dins del rang
					throw new MenuOutOfBoundsException("");
				if (this.selectedOption == options.length) // L'última opció sempre és sortir
					this.selectedOption = 0; // Sortir
			}
		} catch (CampsBuitsException e) {
			clearScreen();
			System.out.println(e.getMessage());
			resetSelection();
			show();
		} catch (NumberFormatException e) {
			clearScreen();
			System.out.println(e.getMessage());
			resetSelection();
			show();
		} catch (MenuOutOfBoundsException e) {
			clearScreen();
			System.out.println(e.getMessage());
			resetSelection();
			show();
		}
	}

	private void resetSelection() {
		this.selection = null;
		this.selectedOption = -99;
	}

}
