package com.video.persistence.daos;

import java.util.Set;

import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.persistence.repos.IDataSource;

public class DaoFacade implements IDaoFacade{
	private UsuariDao udao;
	private VideoDao vdao;
	private TagDao tdao;
	

	public DaoFacade(IDataSource ds) {
		
		this.udao = UsuariDaoImpl.getInstance(ds);
		this.vdao = VideoDaoImpl.getInstance(ds);
		this.tdao = TagDaoImpl.getInstance(ds);
	}

	@Override
	public boolean addUser(Usuari user) {
		return this.udao.addUser(user);
	}
	@Override
	public boolean addVideo(Video video) {
		return this.vdao.addVideo(video);
	}
	@Override
	public boolean addTag(Tag tag) {
		return this.tdao.addTag(tag);
	}
	@Override
	public boolean addVideoToUser(Usuari user, Video video) {
//		Video v = this.vdao.getVideo(video.getUrl());
//		if(v == null) {
//			this.vdao.addVideo(video);
//			v = video;
//		}
		Usuari u = this.udao.getUser(user.getUsername());
		return udao.addVideo(u,video);		
	}
	
	@Override
	public boolean userHasVideo(Usuari user, Video video) {
		Video v = this.vdao.getVideo(video.getUrl());
		if(v == null) {
			this.vdao.addVideo(video);
			v = video;
		}
		Usuari u = this.udao.getUser(user.getUsername());
		return udao.hasVideo(u, v);
	}
	
	@Override
	public boolean addTagToVideo(Tag tag, Video video) {
		return video.getTags().add(tag);
	}

	@Override
	public Video getVideo(String url) {
		return this.vdao.getVideo(url);
	}
	
	@Override
	public Video getVideo(String url, Usuari user) {
		return this.vdao.getVideo(url, user);
	}
	
	@Override 
	public Usuari getUser(String username) {
		return this.udao.getUser(username);
	}
	@Override
	public Set<Tag> getAllTags() {
		return this.tdao.getAllTags();
	}

	@Override
	public Set<Video> getAllVideos() {
		return this.vdao.getAllVideos();
	}
	
	@Override
	public Set<Video> getAllVideos(Usuari user) {
		return this.vdao.getAllVideos(user);
	}

	@Override
	public Set<Usuari> getAllUsuaris() {
		return this.udao.getAllUsuaris();
	}

	@Override
	public Tag getTag(String word) {
		return this.tdao.getTag(word);
	}
}
