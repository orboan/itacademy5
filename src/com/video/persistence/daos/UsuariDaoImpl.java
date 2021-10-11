package com.video.persistence.daos;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.DataSourceNotImplementedException;
import com.video.persistence.repos.IDataSource;

public class UsuariDaoImpl implements UsuariDao{
	
	private IDataSource dataSource;
	
	private static UsuariDaoImpl udao; 
	
	private UsuariDaoImpl(IDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/*
	 * SINGLETON
	 * però de manera que si canvia el DataSource
	 * se li assigna el nou DataSource al singleton
	 */
	public static UsuariDaoImpl getInstance(IDataSource dataSource) {
		if(udao == null)
			return new UsuariDaoImpl(dataSource);
		else {
			if(!dataSource.getClass().getSimpleName().equals(
					udao.dataSource.getClass().getSimpleName()))
				udao.dataSource = dataSource;
			return udao;
		}
	}
	

	@Override
	public boolean addUser(Usuari user) {
		boolean done = false;
		try {
			 done = this.dataSource.add(user);
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return done;
	}

	/**
	 * @param username Nom d'usuari (únic) que volem
	 * buscar al repositori
	 * 
	 * @return Un usuari del repo si n'hi ha un 
	 * amb username igual al del paràmetre
	 * 
	 * @return null si no hi ha cap usuari al repo
	 * amb username igual al que li passem com a paràmetre
	 */	
	@Override
	public Usuari getUser(String username) {
		Usuari newInstance = new Usuari("", "", username, new Date());
		//Busca per hash al MySet enlloc de per iteració
		//és molt més eficient
		Usuari u = null;
		try {
			 u = this.dataSource.getAllUsers().get(newInstance);
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return u;		
	}

	@Override
	public boolean addVideo(Usuari u, Video v) {				
		boolean done = false;
		try {
			 done = this.dataSource.getVideosUserMap().get(u).add(v);
			 if(done && v.getDataDePujada() == null)
				 v.setDataDePujada(LocalDate.now());
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return done;
	}
	
	@Override
	public boolean hasVideo(Usuari u, Video v) {
		boolean done = false;
		try {
			 done = this.dataSource.getVideosUserMap().get(u).contains(v);
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return done;		
	}

	@Override
	public Set<Usuari> getAllUsuaris() {		
		Set<Usuari> u = null;
		try {
			 u = this.dataSource.getAllUsers();
			 if(u == null)
				 throw new DataSourceNotImplementedException("");
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return u;			
	}


	
}
