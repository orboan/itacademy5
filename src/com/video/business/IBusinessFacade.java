package com.video.business;

import java.util.Set;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;

public interface IBusinessFacade {
	
	//Mètodes contra capa de persistència
	public boolean addUsuari(Usuari user) throws Exception;
	public Usuari getUsuari(String username)throws Exception;
	public boolean addVideo(Video video) throws Exception;
	public Video getVideo(String url) throws Exception;
	public Video getVideo(String url, Usuari user) throws Exception;
	public boolean addVideoToUser(Usuari user, Video video);
	public boolean addTagToVideo(Video video, Tag tag);
	public boolean addTag(Tag tag) throws Exception;
	public Tag getTag(String word) throws Exception;
	public Set<Tag> getAllTags() throws Exception;
	public Set<Video> getAllVideos() throws Exception;
	public Set<Video> getAllVideos(Usuari user) throws Exception;
	public Set<Usuari> getAllUsuaris() throws Exception;
	public boolean userHasVideo(Usuari user, Video video) throws Exception;
	
	//Mètodes creacionals
	public Usuari createUsuari(String name, String surname, String username)  throws Exception; 
	public Video createVideo(String title, String url)  throws Exception;
	public Tag createTag(String word)  throws Exception;
}
