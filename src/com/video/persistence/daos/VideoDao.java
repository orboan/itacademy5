package com.video.persistence.daos;

import java.util.Set;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;

public interface VideoDao {
	public boolean addVideo(Video video);	
	public Video getVideo(String url);
	public Video getVideo(String url, Usuari user);
	public Set<Video> getAllVideos();
	public Set<Video> getAllVideos(Usuari user);
}
