package com.video.presentation.controllers;

import com.video.business.IBusinessFacade;
import com.video.presentation.views.FormTui;
import com.video.presentation.views.MenuTui;

public abstract class BeanController {
	
	protected IBusinessFacade business;
	protected FormTui form;
	protected MenuTui menu;
	
	public abstract <T> T build();
	public abstract boolean save();
	
	protected void setForm(FormTui form) {
		this.form = form;
	}
	protected void setMenu(MenuTui menu) {
		this.menu = menu;
	}
}
