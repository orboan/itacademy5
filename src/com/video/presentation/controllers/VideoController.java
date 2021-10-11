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

public class VideoController extends BeanController {

	private Video video;

	private static VideoController c;

	private VideoController(FormTui form, IDataSource ds) {
		this.form = form;
		this.business = new BusinessFacade(ds);
	}
	private VideoController(MenuTui menu, IDataSource ds) {
		this.menu = menu;
		this.business = new BusinessFacade(ds);
	}
	private VideoController(Video video, IDataSource ds) {
		this.video = video;
		this.business = new BusinessFacade(ds);
	}

	public static VideoController getInstance(FormTui form, IDataSource ds) {
		if (c == null) {
			c = new VideoController(form, ds);
		} else {
			c.setForm(form);
		}
		return c;
	}
	
	public static VideoController getInstance(MenuTui menu, IDataSource ds) {
		if (c == null) {
			c = new VideoController(menu, ds);
		} else {
			c.setMenu(menu);
		}
		return c;
	}
	
	public static VideoController getInstance(Video video, IDataSource ds) {
		if (c == null) {
			c = new VideoController(video, ds);
		} else {
			c.setVideo(video);
		}
		return c;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Video build() {
		try {
			this.video = this.business.createVideo(this.form.getCamps().get(0).getAnswer(),
					this.form.getCamps().get(1).getAnswer());
			this.video.setDurada(Integer.parseInt(this.form.getCamps().get(2).getAnswer()));
			if (this.video == null)
				throw new AlreadyExistsInDataSourceException("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return this.video;
	}
	@Override
	public boolean save() {
		boolean added = false;
		if (this.video != null) {
			IBusinessFacade controller = this.business;
			try {
				added = controller.addVideo(this.video);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return added;
	}
	
	public Set<Video> getAllVideos() {
		Set<Video> videos =  null;
		try {
			videos = this.business.getAllVideos();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return videos;
	}
	
	public Set<Video> getAllVideos(Usuari user) {
		Set<Video> videos =  null;
		try {
			videos = this.business.getAllVideos(user);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return videos;
	}
}
