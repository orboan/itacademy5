package com.video.persistence.repos;

import java.util.Map;
import com.video.domain.Tag;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.persistence.datasets.IMySet;

//DUMMY CLASS
public class MySqlRepo implements IDataSource{
	
	//SINGLETON
	private static MySqlRepo repo;

	private MySqlRepo() {
	}

	public static MySqlRepo getInstance() {
		if (repo == null)
			repo = new MySqlRepo();
		return repo;
	}
	
	
	@Override
	public Map<Usuari, IMySet<Video>> getVideosUserMap() {
		// TODO Auto-generated method stub
		System.out.println("MySQL: obtenint mapa d'usuaris amb les seves llistes de vídeos");
		return null;
	}

	@Override
	public IMySet<Video> getAllVideos() {
		// TODO Auto-generated method stub
		System.out.println("MySQL: obtenint el conjunt de tots els vídeos guardats a la base de dades.");
		return null;
	}

	@Override
	public IMySet<Tag> getAllTags() {
		// TODO Auto-generated method stub
		System.out.println("MySQL: obtenint el conjunt de tots les etiquetes guardades a la base de dades.");
		return null;
	}

	@Override
	public IMySet<Usuari> getAllUsers() {
		// TODO Auto-generated method stub
		System.out.println("MySQL: obtenint el conjunt de tots els usuaris guardats a la base de dades.");
		return null;
	}

	@Override
	public boolean add(Usuari user) {
		// TODO Auto-generated method stub
		System.out.println("MySQL: guardant un nou usuari a la base de dades.");
		return false;
	}

	@Override
	public IMySet<Video> getAllVideos(Usuari user) {
		// TODO Auto-generated method stub
		return null;
	}

}
