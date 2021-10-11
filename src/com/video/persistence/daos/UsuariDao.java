package com.video.persistence.daos;

import java.util.Set;

import com.video.domain.Usuari;
import com.video.domain.Video;

public interface UsuariDao {

	public boolean addUser(Usuari user);
	public Usuari getUser(String username);
	public boolean addVideo(Usuari u, Video v);
	public boolean hasVideo(Usuari u, Video v);
	public Set<Usuari> getAllUsuaris();
	
	
}
