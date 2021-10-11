package com.video.presentation.controllers;

import java.util.Set;

import com.video.business.BusinessFacade;
import com.video.business.IBusinessFacade;
import com.video.domain.Usuari;
import com.video.domain.Video;
import com.video.exceptions.AlreadyExistsInDataSourceException;
import com.video.persistence.repos.IDataSource;
import com.video.presentation.views.FormTui;
import com.video.presentation.views.MenuTui;

public class UsuariController extends BeanController {

	private Usuari user;

	private static UsuariController c;

	private UsuariController(FormTui form, IDataSource ds) {
		this.form = form;
		this.business = new BusinessFacade(ds);
	}
	
	private UsuariController(MenuTui menu, IDataSource ds) {
		this.menu = menu;
		this.business = new BusinessFacade(ds);
	}
	
	private UsuariController(Usuari user, IDataSource ds) {
		this.user = user;
		this.business = new BusinessFacade(ds);
	}

	public static UsuariController getInstance(FormTui form, IDataSource ds) {
		if (c == null) {
			c = new UsuariController(form, ds);
		} else {
			c.setForm(form);
		}
		return c;
	}
	
	public static UsuariController getInstance(Usuari user, IDataSource ds) {
		if (c == null) {
			c = new UsuariController(user, ds);
		} else {
			c.setUser(user);
		}
		return c;
	}
	
	public static UsuariController getInstance(MenuTui menu, IDataSource ds) {
		if (c == null) {
			c = new UsuariController(menu, ds);
		} else {
			c.setMenu(menu);
		}
		return c;
	}
	
	public void setUser(Usuari user) {
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuari build() {
		try {
			this.user = this.business.createUsuari(this.form.getCamps().get(0).getAnswer(),
					this.form.getCamps().get(1).getAnswer(), this.form.getCamps().get(2).getAnswer());
			if (this.user == null)
				throw new AlreadyExistsInDataSourceException("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return this.user;
	}
	
	@Override
	public boolean save() {
		boolean added = false;
		if (this.user != null) {
			IBusinessFacade controller = this.business;
			try {
				added = controller.addUsuari(this.user);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return added;
	}
	
	public boolean videoExistsInUserAccount(Video video) {
		try {
			return this.business.userHasVideo(this.user, video);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addVideoToUser(Video video) {
		try {
			return this.business.addVideoToUser(this.user, video);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Set<Usuari> getAllUsuaris(){
		Set<Usuari> users = null;
		try {
			users = this.business.getAllUsuaris();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
}
