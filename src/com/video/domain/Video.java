package com.video.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Video {

	//Patró State (que internament té un enum)
	private PlayerState playerState;
	
	//enum
	private UploadState uploadState;

	private String title;

	// La URL és única per a cada vídeo
	private final String url;

	private LocalDate dataDePujada;
	
	private int durada = 10; //durada del vídeo en segons

	private List<Tag> tags = new ArrayList<>();

	public Video(String url) {
		this.url = url;
	}

	public Video(String title, String url) {
		this.playerState = new StoppedState(this);
		this.title = title;
		this.url = url;
	}

	public void changePlayerState(PlayerState state) {
		this.playerState = state;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}	

	public UploadState getUploadState() {
		return uploadState;
	}

	public void setUploadState(UploadState uploadState) {
		this.uploadState = uploadState;
		System.out.println("Video " + title + " is " + this.uploadState.name());
		if(uploadState.equals(UploadState.UPLOADED)) {
			System.out.println("Uploaded finished at: " + this.dataDePujada.toString());
		}
	}

	public boolean addTag(Tag tag) {
		return tags.add(tag);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}	
	
	public int getDurada() {
		return durada;
	}

	public void setDurada(int durada) {
		this.durada = durada;
	}

	public LocalDate getDataDePujada() {
		return dataDePujada;
	}

	public void setDataDePujada(LocalDate dataDePujada) {
		dataDePujada.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
		this.dataDePujada = dataDePujada;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public boolean tagExists(Tag t) {
		return tags.contains(t);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		// La URL és única per a cada vídeo
		Video v = (Video) o;
		return (this.getUrl().equals(v.getUrl()));
	}

	@Override
	public int hashCode() {
		final int PRIME = 41;
		int result = 7;
		result = PRIME * result + this.getUrl().hashCode();
		return result;
	}

	@Override
	public String toString() {
		String uploadDate;
		if (this.dataDePujada == null)
			uploadDate = "video no pujat!!";
		else
			uploadDate = dataDePujada.toString();
		StringBuilder sb = (new StringBuilder()).append("\nVideo: \n------\nTitle:\t").append(this.title)
				.append("\nData de pujada: " + uploadDate).append("\nUrl:\t ").append(this.url).append("\nTags:\t");
		for (Tag tag : tags) {
			sb.append(tag.getWord() + " ");
		}
		return sb.toString();
	}

	static public enum UploadState {
		UPLOADING, VERIFYING, UPLOADED
	}

}
