package com.video.presentation.controllers;

import java.util.Scanner;

import com.video.business.BusinessFacade;
import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.persistence.repos.IDataSource;
import com.video.presentation.i18n.Messages;
import com.video.presentation.views.FormTui;
import com.video.presentation.views.MenuTui;
import com.video.presentation.views.TagFormTui;
import com.video.presentation.views.VideoFormTui;
import com.video.presentation.views.VideoPlayerGui;

public class MainController {

	private final Scanner in;

	final IDataSource ds;

	Messages messages = Messages.getInstance();

	private BusinessFacade business;

	boolean exit;

	// SINGLETON
	private static MainController controller;

	private MainController(Scanner in, IDataSource ds) {
		this.in = in;
		this.ds = ds;
		this.business = new BusinessFacade(ds);
	}

	public static MainController getInstance(Scanner in, IDataSource ds) {
		if (controller == null)
			return new MainController(in, ds);
		return controller;
	}

	public void run() {
		boolean continueTui = true;

		boolean isStart = true;

		while (continueTui) {

			if (!isStart) {
				pressEnterKeyToContinue();
			}
			isStart = false;
			// LOGIN
			// o donar d'alta usuari
			Usuari user = setUsuari();
			clearScreen();
			System.out.println("\nUsuari: " + user.getUsername());

			// Un cop tenim l'usuari
			// preguntem si vol pujar un nou vídeo
			// o seleccionar-ne un de la llista
			Video selectedVideo = addOrSelectToPlayVideos(user);
			if (this.exit) {
				printGoodBye();
				break;
			}

			if (!this.exit && selectedVideo != null) {
				System.out.println("\n\n--- Selected video is:\n" + selectedVideo.toString());

				if (selectedVideo.getDataDePujada() != null) {
					System.out.println("\nAmb el reproductor gràfic pots canviar l'estat del vídeo.\n"
							+ "Stopped | Playing | Paused\n");
					VideoPlayerGui player = new VideoPlayerGui(selectedVideo);
					player.init();
				} else {
					System.out.println("\nInfo: el vídeo NO s'ha pujat, per tant No es pot reproduir.");
				}
			}

		} // FINAL BUCLE
	} // Final run()

	// Estableix un usuari inicial fictici
	private Usuari setUsuari() {
		Usuari user = null;
		String nom = "Josep";
		String cognom = "Amic";
		String username = "jamic";
		try {
			user = this.business.createUsuari(nom, cognom, username);
			if (user == null)
				user = this.business.getUsuari(username);
			else {
				UsuariController ucontroller = UsuariController.getInstance(user, ds);
				ucontroller.save();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	// Ofereix l'usuari crear un nou vídeo (per a pujar)
	// o seleccionar-ne un de ja existent
	private Video addOrSelectToPlayVideos(Usuari user) {
		Video video = null;
		MenuTui m1 = createNewOrListVideosMenu();
		m1.show();
		if (m1.isQuit()) {
			this.exit = true; // exit
		}
		int selectedOption = m1.getSelectedOption();
		if (selectedOption == 1) {// To create new video
			video = addNewVideo(false); // create a new Video
			// boolean is used to skip the creation of new vid
			// when called recursively to add new tags to the video
			if (this.exit) {
				return null;
			}
			if (video == null) {
				System.out.println(messages.newVideoAlreadyExistsInRepoInfo);
			} else {
				// Upload the new video into user's account?
//				...
				try {
					UploadVideo upload = new UploadVideo(this);
					upload.setVideo(video);
					upload.setUser(user);
					upload.t.start();
					upload.t.join();
					if (!this.exit) {
						upload = new UploadVideo(this);
						// Upload video to public repo: Wanna make this video PUBLIC?
						upload.setVideo(video);
						upload.t.start();
						upload.t.join();
					}
				} catch (InterruptedException e) {

				}
			}

		} else if (selectedOption == 2) {
			video = selectToPlayVideos(createAccountVideosListMenu(user), user, messages.emptyPrivateListInfo);
		} else if (selectedOption == 3) {
			video = selectToPlayVideos(createPublicVideosListMenu(), user, messages.emptyPublicListInfo);
		}
		return video;
	}

	// Per a seleccionar un vídeo ja existent (públic o privat)
	private Video selectToPlayVideos(MenuTui menu, Usuari user, String message) {
		Video video = null;
		MenuTui vidsListMenu = menu;
		if (vidsListMenu.size() < 2) {
			System.out.println(message);
			return addOrSelectToPlayVideos(user);
		} else {
			vidsListMenu.show();
			if (vidsListMenu.isQuit()) {
				this.exit = true; // exit
				return null;
			}
			String url = vidsListMenu.getSelectedTextOption().split(":")[1].trim();
			try {
				video = business.getVideo(url);
				if (video == null) {
					video = business.getVideo(url, user);
				}
				System.out.println(messages.selectedVideoInfo + video.getUrl()); // feedback
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return video;
	}

	/*
	 * Crea un nou vídeo amb les dades introduides per l'usuari a través del
	 * formulari TUI de creació de vídeo. Opcionalment podem assignar etiquetes al
	 * vídeo. Atenció: NO puja el vídeo.
	 */
	private Video addNewVideo(boolean jumpToTags) {
		Video createdVideo = null;
		FormTui vform = null;
		BeanController vcontroller = null;
		if (!jumpToTags) {
			vform = new VideoFormTui(in, this);
			vcontroller = VideoController.getInstance(vform, ds);
			vform.show();
			createdVideo = vcontroller.build();
			if (createdVideo != null) {
				jumpToTags = true;
			}
		}

		if (jumpToTags) {
			this.exit = addTags(createdVideo, false);
		}

		return createdVideo;
	}

	// Afegir Tags a un vídeo
	private boolean addTags(Video video, boolean skipAskAddTagsMenu) {
		MenuTui m1 = null;
		if (!skipAskAddTagsMenu) {
			m1 = createAskAddTagsMenu();
			m1.show();

			if (m1.isQuit()) {
				return true; // exit
			}
		}

		skipAskAddTagsMenu = false;
		// selectedOption = 1 per a '1' (sí vull afegir tags al vídeo)
		// selectedOption = -1 per a 's' (sí vull afegir tags al vídeo)
		if (m1 == null || m1.getSelectedOption() == 1 || m1.getSelectedOption() == -1) { // TAGS
			Tag t = null;
			MenuTui m = createNewOrListTagMenu();
			m.show();
			if (m.isQuit()) {
				return true; // exit
			}
			int selectedOption = m.getSelectedOption();
			if (selectedOption == 1 || selectedOption == -1) {// Create new tag FORM

				FormTui tform = new TagFormTui(in, this);
				BeanController tcontroller = TagController.getInstance(tform, ds);
				tform.show();
				t = tcontroller.build();// return null if tag already exists in repo
				if (t != null) { // then save to repo
					boolean added = tcontroller.save(); // save tag to repository
					if (added)
						System.out.println(messages.newTagCreatedAndSavedToRepoInfo);// feedback
				} else
					System.out.println(messages.newTagAlreadyExistsInRepoInfo);

			} else if (selectedOption == 2 || selectedOption == -2) {// List of tags MENU

				MenuTui tagsListMenu = createTagsListMenu();
				if (tagsListMenu.size() < 2) {
					System.out.println(messages.emptyTagsListInfo);
					skipAskAddTagsMenu = true;
					return addTags(video, skipAskAddTagsMenu);
				} else {
					tagsListMenu.show();
					if (tagsListMenu.isQuit()) {
						return true; // exit
					}
					String word = tagsListMenu.getSelectedTextOption().split("-")[0].trim();
					try {
						t = business.getTag(word);
						System.out.println(messages.selectedTagInfo + t.getWord()); // feedback
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				t = new Tag("Uncategorized");
			}

			// Només s'executa si estem creant un vídeo:
			if (t != null && video != null) { // Volem afegir etiqueta al vídeo?
				MenuTui menu = createAssignTagToVideoMenu(t);
				menu.show();
				if (menu.getSelectedOption() == 1 || menu.getSelectedOption() == -1) {
					// Check if tag already is added to the video
					if (video.tagExists(t)) {
						System.out.println(messages.tagAlreadyExistsInfo);// feedback
					} else {
						video.addTag(t);// add tag to video
						System.out.println(messages.tagAddedToVideoInfo);// feedback
					}
				}
			}
			// Només s'executa si estem creant un vídeo:
			// Vol afegir un altre tag al video?
			if (video != null) {
				MenuTui men1 = createAddAnotherTagMenu(); // Més tags?
				men1.show();
				if (men1.getSelectedOption() == 1 || men1.getSelectedOption() == -1) {
					skipAskAddTagsMenu = true;
					return addTags(video, skipAskAddTagsMenu); // Sí volem afegir més tags
				} else {
					return false; // No més tags
				}
			}

		} // Final TAGS
		return false; // No més tags
	} // Final addTag()

	// ##### Inici Creació Menus #####
	private MenuTui createNewOrListTagMenu() {
		MenuTui m = new MenuTui(this.in);
		m.setDecoration(" --- ");
		m.setQuestion(messages.createOrListTagsOptionsQuestion);
		m.setOptions(
				new String[] { messages.createNewTagOption, messages.listTagsFromListOption, messages.quitOption });
		m.setFooter(messages.simpleFooter);
		return m;
	}

	private MenuTui createNewOrListVideosMenu() {
		MenuTui m = new MenuTui(this.in);
		m.setDecoration(" --- ");
		m.setQuestion(messages.createOrListVideosOptionsQuestion);
		m.setOptions(new String[] { messages.createNewVideoOption, messages.listVideosFromAccountListOption,
				messages.listVideosFromPublicListOption, messages.quitOption });
		m.setFooter(messages.simpleFooter);
		return m;
	}

	private MenuTui createAskAddTagsMenu() {
		MenuTui m1 = new MenuTui(this.in);
		m1.setQuestion(messages.videoWannaAddTagsOptionsQuestion);
		m1.setOptions(new String[] { messages.yesOption, messages.noOption, messages.quitOption });
		m1.setFooter(messages.simpleFooter);
		return m1;
	}

	private MenuTui createPublicVideosListMenu() {
		MenuTui vidsListMenu = new MenuTui(in);
		vidsListMenu.setDecoration(" --- ");
		vidsListMenu.setQuestion(messages.chooseVideoFromListQuestion);
		vidsListMenu.setOptionsFromDataSource("Video", ds);
		vidsListMenu.setFooter(messages.simpleFooter);
		return vidsListMenu;
	}

	private MenuTui createAccountVideosListMenu(Usuari user) {
		MenuTui vidsListMenu = new MenuTui(in);
		vidsListMenu.setDecoration(" --- ");
		vidsListMenu.setQuestion(messages.chooseVideoFromAccountListQuestion);
		vidsListMenu.setOptionsFromDataSource("Video", ds, user);
		vidsListMenu.setFooter(messages.simpleFooter);
		return vidsListMenu;
	}

	private MenuTui createTagsListMenu() {
		MenuTui tagsListMenu = new MenuTui(in);
		tagsListMenu.setDecoration(" --- ");
		tagsListMenu.setQuestion(messages.chooseTagFromListQuestion);
		tagsListMenu.setOptionsFromDataSource("Tag", ds);
		tagsListMenu.setFooter(messages.simpleFooter);
		return tagsListMenu;
	}

	MenuTui createAssignVideoToUserMenu(Video v) {
		MenuTui menu = new MenuTui(this.in);
		menu.setQuestion(messages.userAssignVideoToUsuariOptionsQuestion + "\nTítol: " + v.getTitle());
		menu.setOptions(new String[] { messages.yesOption, messages.noOption, messages.quitOption });
		menu.setFooter(messages.simpleFooter);
		return menu;
	}

	MenuTui createMakeVideoPublicMenu(Video v) {
		MenuTui menu = new MenuTui(this.in);
		menu.setQuestion(messages.userAssignVideoToPublicOptionsQuestion + "\nTítol: " + v.getTitle());
		menu.setOptions(new String[] { messages.yesOption, messages.noOption, messages.quitOption });
		menu.setFooter(messages.simpleFooter);
		return menu;
	}

	private MenuTui createAssignTagToVideoMenu(Tag t) {
		MenuTui menu = new MenuTui(this.in);
		menu.setQuestion(messages.videoAssignTagToVideoOptionsQuestion + "\nEtiqueta: " + t.getWord());
		menu.setOptions(new String[] { messages.yesOption, messages.noOption, messages.quitOption });
		menu.setFooter(messages.simpleFooter);
		return menu;
	}

	private MenuTui createAddAnotherTagMenu() {
		MenuTui m1 = new MenuTui(this.in);
		m1.setQuestion(messages.videoWannaAddAnotherTagOptionsQuestion);
		m1.setOptions(new String[] { messages.yesOption, messages.noOption, messages.quitOption });
		m1.setFooter(messages.simpleFooter);
		return m1;
	}
	// ##### Final Creació Menus #####

	// Other helpers
	private void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

	private void pressEnterKeyToContinue() {
		System.out.println("Prem Enter per a continuar...");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	private void printGoodBye() {
		System.out.println("\n -- Has sortit del programa -- \n Adéu!!");
	}

}
