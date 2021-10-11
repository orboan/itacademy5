package com.video.business;

import java.util.Date;
import java.util.Set;

import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.BadDataSourceException;
import com.video.persistence.daos.DaoFacade;
import com.video.persistence.daos.IDaoFacade;
import com.video.persistence.repos.IDataSource;

public class UsuariBusiness {
	
	private IDaoFacade dao;
	
	//SINGLETON
	private static UsuariBusiness controller;
	
	private UsuariBusiness() {}
	public static UsuariBusiness getInstance() {
		if(controller == null)
			controller = new UsuariBusiness();
		return controller;
	}
	
	public void setDataSource(IDataSource dataSource) {
		this.dao = new DaoFacade(dataSource);
	}
	
	//---- Mètodes d'accés a repositori ----
	
	protected boolean addUsuari(Usuari user) throws BadDataSourceException {
		if(dao != null)
			return dao.addUser(user);
		throw new BadDataSourceException("");
	}
	
	protected Usuari getUsuari(String username) throws BadDataSourceException {
		if(dao != null) 
			return dao.getUser(username);
		throw new BadDataSourceException("");
	}
	
	protected boolean addVideoToUser(Usuari user, Video video) {
		return this.dao.addVideoToUser(user, video);
	}
	
	protected Set<Usuari> getAllUsuaris() throws BadDataSourceException {
		if(dao != null)
			return dao.getAllUsuaris();
		throw new BadDataSourceException("");
	}
	
	//---- Mètodes creacionals ----
	
	protected Usuari createUsuari(String name, String surname, String username) throws BadDataSourceException {
		String usernameFirst = username.trim().split(" ")[0];
		if(usuariExists(usernameFirst)) {
			return null; //No es pot crear usuari pq ja existeix
		}
		return new Usuari(name, surname, username, new Date() );
	}
	
	protected boolean usuariExists(String username) throws BadDataSourceException {
		Usuari user = getUsuari(username);
		if(user == null) {
			return false;
		}
		return true;
	}
}
