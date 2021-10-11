package com.video.persistence.daos;

import java.util.Set;

import com.video.domain.Tag;
import com.video.exceptions.DataSourceNotImplementedException;
import com.video.persistence.repos.IDataSource;

public class TagDaoImpl implements TagDao {
	private IDataSource dataSource;
	
	
	private static TagDaoImpl tdao; 
	
	private TagDaoImpl(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 * SINGLETON
	 * però de manera que si canvia el DataSource
	 * se li assigna el nou DataSource al singleton
	 */
	public static TagDaoImpl getInstance(IDataSource dataSource) {
		if(tdao == null)
			return new TagDaoImpl(dataSource);
		else {
			if(!dataSource.getClass().getSimpleName().equals(
					tdao.dataSource.getClass().getSimpleName()))
				tdao.dataSource = dataSource;
			return tdao;
		}
	}		
	
	@Override
	public boolean addTag(Tag tag) {		
		boolean done = false;
		try {
			 done = this.dataSource.getAllTags().add(tag);
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return done;		
	}
	
	@Override
	public Tag getTag(String word) {		
		Tag t = null;
		try {
			 t = this.dataSource.getAllTags().get(new Tag(word));
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return t;	
	}

	@Override
	public Set<Tag> getAllTags() {
		Set<Tag> t = null;
		try {
			 t = this.dataSource.getAllTags();
			 if(t == null)
				 throw new DataSourceNotImplementedException("");
		} catch (DataSourceNotImplementedException e) {
			System.out.println(e.getMessage());
		}
		return t;
	}


}
