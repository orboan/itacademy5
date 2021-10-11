package com.video.persistence.daos;

import java.time.LocalDate;
import java.util.Set;

import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.DataSourceNotImplementedException;
import com.video.persistence.repos.IDataSource;

public class VideoDaoImpl implements VideoDao{
	private IDataSource dataSource;
	
	private static VideoDaoImpl vdao; 
	
	private VideoDaoImpl(IDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/*
	 * SINGLETON
	 * però de manera que si canvia el DataSource
	 * se li assigna el nou DataSource al singleton
	 */
	public static VideoDaoImpl getInstance(IDataSource dataSource) {
		if(vdao == null)
			vdao = new VideoDaoImpl(dataSource);
		else {
			if(!dataSource.getClass().getSimpleName().equals(
					vdao.dataSource.getClass().getSimpleName()))
				vdao.dataSource = dataSource;			
		}
		return vdao;
	}
	
	
	@Override
	public boolean addVideo(Video video) {				
		boolean done = false;
		try {
			 done = this.dataSource.getAllVideos().add(video);
			 if(done && video.getDataDePujada() == null)
				 video.setDataDePujada(LocalDate.now());
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return done;
	}

	@Override
	public Video getVideo(String url) {				
		Video v = null;
		try {
			 v = this.dataSource.getAllVideos().get(new Video(url));
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return v;	
	}
	
	@Override
	public Video getVideo(String url, Usuari user) {				
		Video v = null;
		try {
			 v = this.dataSource.getAllVideos(user).get(new Video(url));			 
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return v;	
	}

	@Override
	public Set<Video> getAllVideos() {		
		Set<Video> v = null;
		try {
			 v = this.dataSource.getAllVideos();
			 if(v == null)
				 throw new DataSourceNotImplementedException("");
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return v;	
	}

	@Override
	public Set<Video> getAllVideos(Usuari user) {
		Set<Video> v = null;
		try {
			 v = this.dataSource.getAllVideos(user);
			 if(v == null)
				 throw new DataSourceNotImplementedException("");
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return v;	
	}
}
