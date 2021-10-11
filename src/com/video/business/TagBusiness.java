package com.video.business;

import java.util.Set;

import com.video.domain.Tag;
import com.video.exceptions.BadDataSourceException;
import com.video.persistence.daos.DaoFacade;
import com.video.persistence.daos.IDaoFacade;
import com.video.persistence.repos.IDataSource;

public class TagBusiness {

	private IDaoFacade dao;
	
	//SINGLETON
	private static TagBusiness controller;
	
	private TagBusiness() {}
	protected static TagBusiness getInstance() {
		if(controller == null)
			return new TagBusiness();
		return controller;
	}
	
	protected void setDataSource(IDataSource dataSource) {
		this.dao = new DaoFacade(dataSource);
	}
	
	protected boolean addTag(Tag tag) throws BadDataSourceException {
		if(dao != null)
			return dao.addTag(tag);
		throw new BadDataSourceException("");
	}
	
	protected Set<Tag> getAllTags() throws BadDataSourceException {
		if(dao != null)
			return dao.getAllTags();
		throw new BadDataSourceException("");
	}
	
	protected Tag getTag(String word) throws BadDataSourceException {
		if(dao != null)
			return dao.getTag(word);
		throw new BadDataSourceException("");
	}
		
	//---- Mètodes creacionals ----
	
	protected Tag createTag(String word) throws BadDataSourceException {
		if(tagExists(word)) {
			return null; //No es pot crear usuari pq ja existeix
		}
		return new Tag(word);
	}
	
	protected boolean tagExists(String word) throws BadDataSourceException {
		Tag t = getTag(word);
		if(t == null) {
			return false;
		}
		return true;
	}
	
}
