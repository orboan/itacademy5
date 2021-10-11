package com.video.persistence.daos;

import java.util.Set;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;

public interface IDaoFacade{
	public boolean addUser(Usuari user);
	public boolean addVideo(Video video);
	public boolean addTag(Tag tag);
	public boolean addVideoToUser(Usuari user, Video video);
	public boolean userHasVideo(Usuari user, Video video);
	public boolean addTagToVideo(Tag tag, Video video);
	
	public Video getVideo(String url);
	public Video getVideo(String url, Usuari user);
	
	public Tag getTag(String word);
		
	public Usuari getUser(String username);
	
	public Set<Tag> getAllTags();
	public Set<Video> getAllVideos();// for all users
	public Set<Video> getAllVideos(Usuari user);
	public Set<Usuari> getAllUsuaris();
}
