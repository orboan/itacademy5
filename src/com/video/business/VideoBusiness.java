package com.video.business;

import java.util.Set;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.BadDataSourceException;
import com.video.persistence.daos.DaoFacade;
import com.video.persistence.daos.IDaoFacade;
import com.video.persistence.repos.IDataSource;

public class VideoBusiness {

	private IDaoFacade dao;
	
	//SINGLETON
	private static VideoBusiness controller;
	
	private VideoBusiness() {}
	public static VideoBusiness getInstance() {
		if(controller == null)
			controller = new VideoBusiness();
		return controller;
	}
	
	protected void setDataSource(IDataSource dataSource) {
		this.dao = new DaoFacade(dataSource);
	}
	
	protected boolean addVideo(Video video) throws BadDataSourceException {
		if(dao != null)
			return dao.addVideo(video);
		throw new BadDataSourceException("");
	}
	
	protected Video getVideo(String url) throws BadDataSourceException {
		if(dao != null)
			return dao.getVideo(url);
		throw new BadDataSourceException("");
	}
	
	protected Video getVideo(String url, Usuari user) throws BadDataSourceException {
		if(dao != null)
			return dao.getVideo(url, user);
		throw new BadDataSourceException("");
	}
	
	protected boolean addTagToVideo(Video video, Tag tag) {
		return this.dao.addTagToVideo(tag, video);
	}
	
	protected Set<Video> getAllVideos() throws BadDataSourceException {
		if(dao != null)
			return dao.getAllVideos();
		throw new BadDataSourceException("");
	}
	
	protected Set<Video> getAllVideos(Usuari user) throws BadDataSourceException {
		if(dao != null)
			return dao.getAllVideos(user);
		throw new BadDataSourceException("");
	}
	
	//---- Mètodes creacionals ----
	
	protected Video createVideo(String title, String url) throws BadDataSourceException {
		//TODO checkUrl() throws BadUrlFormatException
		if(videoExists(url)) {
			return null; //No es pot crear video pq ja existeix
		}
		return new Video(title, url);
	}
	
	protected boolean videoExists(String url) throws BadDataSourceException {
		Video v = getVideo(url);
		if(v == null) {
			return false;
		}
		return true;
	}
}
