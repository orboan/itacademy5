package com.video.business;

import java.util.Set;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.BadDataSourceException;
import com.video.persistence.repos.IDataSource;

public class BusinessFacade implements IBusinessFacade{
	private TagBusiness tc;
	private UsuariBusiness uc;
	private VideoBusiness vc;

	
	public BusinessFacade(IDataSource ds) {
		this.tc = TagBusiness.getInstance();
		this.tc.setDataSource(ds);
		this.uc = UsuariBusiness.getInstance();
		this.uc.setDataSource(ds);
		this.vc = VideoBusiness.getInstance();
		this.vc.setDataSource(ds); 
	}


	@Override
	public boolean addUsuari(Usuari user) throws Exception {
		return uc.addUsuari(user);
	}


	@Override
	public Usuari getUsuari(String username) throws Exception{
		return uc.getUsuari(username);
	}


	@Override
	public boolean addVideo(Video video) throws Exception {
		return vc.addVideo(video);
	}


	@Override
	public Video getVideo(String url) throws Exception {
		return vc.getVideo(url);
	}

	@Override
	public Video getVideo(String url, Usuari user) throws Exception {
		return vc.getVideo(url, user);
	}

	@Override
	public boolean addVideoToUser(Usuari user, Video video) {
		return uc.addVideoToUser(user, video);
	}


	@Override
	public boolean addTagToVideo(Video video, Tag tag) {
		return vc.addTagToVideo(video, tag);
	}


	@Override
	public boolean addTag(Tag tag) throws BadDataSourceException {
		return tc.addTag(tag);
	}


	@Override
	public Set<Tag> getAllTags() throws BadDataSourceException {
		return tc.getAllTags();
	}


	@Override
	public Set<Video> getAllVideos() throws BadDataSourceException {
		return vc.getAllVideos();
	}


	@Override
	public Set<Usuari> getAllUsuaris() throws BadDataSourceException {
		return uc.getAllUsuaris();
	}


	@Override
	public Tag getTag(String word) throws BadDataSourceException {
		return tc.getTag(word);
	}


	@Override
	public Usuari createUsuari(String name, String surname, String username) 
			throws BadDataSourceException {
		return uc.createUsuari(name, surname, username);
	} 


	@Override
	public Video createVideo(String title, String url) throws BadDataSourceException {
		return vc.createVideo(title, url);
	}


	@Override
	public Tag createTag(String word) throws BadDataSourceException {
		return tc.createTag(word);
	}


	@Override
	public boolean userHasVideo(Usuari user, Video video) throws BadDataSourceException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Set<Video> getAllVideos(Usuari user) throws Exception {
		return vc.getAllVideos(user);
	}
}
