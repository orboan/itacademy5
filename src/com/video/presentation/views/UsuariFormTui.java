package com.video.presentation.views;

import java.util.Scanner;

import com.video.domain.Usuari;
import com.video.presentation.controllers.MainController;

public class UsuariFormTui extends FormTui {
	
	private Usuari user;
	
	public UsuariFormTui(Scanner in, MainController controller) {
		this.in = in;
		this.controller = controller;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
