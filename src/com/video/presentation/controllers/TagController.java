package com.video.presentation.controllers;

import java.util.Set;

import com.video.business.BusinessFacade;
import com.video.business.IBusinessFacade;
import com.video.domain.Tag;
import com.video.exceptions.AlreadyExistsInDataSourceException;
import com.video.persistence.repos.IDataSource;
import com.video.presentation.views.FormTui;
import com.video.presentation.views.MenuTui;

public class TagController extends BeanController {
	
	private Tag tag;
	
	private static TagController c;
	private TagController(FormTui form, IDataSource ds) {
		this.form = form;
		this.business = new BusinessFacade(ds);
	}
	private TagController(MenuTui menu, IDataSource ds) {
		this.menu = menu;
		this.business = new BusinessFacade(ds);
	}
	public static TagController getInstance(FormTui form, IDataSource ds) {
		if(c == null) {
			c = new TagController(form, ds);
		} else {
			c.setForm(form);
		}
		return c;
	}
	
	public static TagController getInstance(MenuTui menu, IDataSource ds) {
		if(c == null) {
			c = new TagController(menu, ds);
		} else {
			c.setMenu(menu);
		}
		return c;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Tag build() {
		try {
			this.tag = this.business.createTag(this.form.getCamps().get(0).getAnswer());
			if(this.tag == null) 
				throw new AlreadyExistsInDataSourceException("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return this.tag;
	}
	
	@Override
	public boolean save() {
		boolean added = false;
		if (this.tag != null) {
			IBusinessFacade controller = this.business;
			try {
				added = controller.addTag(this.tag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return added;
	}
	
	public Set<Tag> getAllTags(){
		Set<Tag> tags = null;
		try {
			tags = this.business.getAllTags();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return tags;
	}
	
}
