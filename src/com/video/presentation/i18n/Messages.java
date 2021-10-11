package com.video.presentation.i18n;

public class Messages {

	private static Messages m;
	private Messages() {
		
	}
	public static Messages getInstance() {
		if(m == null)
			m = new Messages();
		return m;
	}
	
	//Video
	public final String videoQuestion1 = "\nIntrodueixi el títol del vídeo: ";
	public final String videoQuestion2 = "\nIntrodueixi la URL del vídeo: ";
	public final String videoQuestion3 = "\nIndiqui la durada del vídeo en segons: ";
	public final String createOrListVideosOptionsQuestion = "\nVol pujar un nou vídeo o "
			+ "reproduir-ne un?:";
	public final String chooseVideoFromListQuestion = "\nSelecciona un video públic de la llista: ";
	public final String chooseVideoFromAccountListQuestion = "\nSelecciona un video de la llista del teu compte: ";
	public final String videoWannaAddTagsOptionsQuestion = "\nVol assignar etiquetes al vídeo?: ";
	public final String videoWannaAddAnotherTagOptionsQuestion = "\nVol seleccionar una altra etiqueta?: "; 
	public final String videoAssignTagToVideoOptionsQuestion = "\nConfirmi que vol assignar aquesta etiqueta al vídeo: ";
	
	public final String createNewVideoOption = "Pujar un nou vídeo";
	public final String listVideosFromPublicListOption = "Reproduir un vídeo públic";
	public final String listVideosFromAccountListOption = "Reproduir un vídeo del meu compte";
	
	//Usuaris
	public final String userAssignVideoToUsuariOptionsQuestion = "\nConfirmi que vol pujar aquest vídeo al seu compte d'usuari:  ";
	public final String userAssignVideoToPublicOptionsQuestion = "\nConfirmi si vol fer públic aquest vídeo:  ";
	public final String userWannaAddAnotherVideoOptionsQuestion = "\nVol pujar o seleccionar un altre vídeo?: ";
	public final String userWannaAddVideosOptionsQuestion = "\nVol pujar un vídeo o seleccionar-ne un del seu compte d'usuari?: ";
	//Tag 
	public final String tagQuestion1 = "\nIntrodueixi el text de l'etiqueta: ";
	public final String createOrListTagsOptionsQuestion = "\nVols crear una nova etiqueta o seleccionar-ne una de la llista?: ";	
	public final String chooseTagFromListQuestion = "\nSelecciona una etiqueta de la llista: ";
	public final String createNewTagOption = "Crear una nova etiqueta";
	public final String listTagsFromListOption = "Llistar les etiquetes existents";
	
	//Binary options
	public final String yesOption = "Sí (s)";
	public final String noOption = "No (n)";
	public final String quitOption = "Sortir del programa (q)";
	public final String saveAndQuitOption = "Guardar i sortir del programa (q)";
	
	//Common elements
	public final String simpleFooter = "\nTria una opció: ";
	
	//Altres feedbacks
	public final String newTagCreatedAndSavedToRepoInfo = "\nInfo: Etigueta creada i guardada amb èxit.";
	public final String newVideoCreatedAndSavedToRepoInfo = "\nInfo: Video fet públic amb èxit.";
	public final String newTagAlreadyExistsInRepoInfo = "\nInfo: Aquesta etiqueta ja existeix al repositori.";
	public final String newVideoAlreadyExistsInRepoInfo = "\nError: Aquest video no es pot crear:\n"
			+ "No es pot crear un vídeo nou si ja existeix al repositori públic.";
	public final String tagAddedToVideoInfo = "\nInfo: Etiqueta afegida al vídeo amb èxit.";
	public final String videoAddedToUserInfo = "\nInfo: Vídeo pujat amb èxit al compte de l'usuari.";
	public final String selectedTagInfo = "\nInfo: L'etiqueta seleccionada és: ";
	public final String selectedVideoInfo = "\nInfo: El vídeo seleccionat és: ";
	public final String emptyPublicListInfo = "\nInfo: El repositori public està buit! ";
	public final String emptyPrivateListInfo = "\nInfo: El repositori privat està buit!";
	public final String emptyTagsListInfo = "\nInfo: Aquest vídeo no té cap etiqueta!";
	public final String tagAlreadyExistsInfo = "\nInfo: Aquesta etiqueta ja existeix al video.";
	public final String videoAlreadyExistsInfo = "\nInfo: Aquest vídeo ja pertany a l'usuari.";
}
