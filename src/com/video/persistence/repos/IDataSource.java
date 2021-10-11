package com.video.persistence.repos;

import java.util.Map;
import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.persistence.datasets.IMySet;

public interface IDataSource {
	
	//Connection con;
	
	public Map<Usuari, IMySet<Video>> getVideosUserMap();

	public IMySet<Video> getAllVideos();
	
	public IMySet<Video> getAllVideos(Usuari user);

	public IMySet<Tag> getAllTags();
	
	public IMySet<Usuari> getAllUsers(); 
	
	public boolean add(Usuari user);

	
}
